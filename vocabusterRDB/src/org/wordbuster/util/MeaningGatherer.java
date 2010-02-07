package org.wordbuster.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.SimpleXmlSerializer;
import org.htmlcleaner.TagNode;
import org.wordbuster.domain.VBWord;
import org.wordbuster.domain.VBWordInfo;

/**
 * Designed for Google dictionary
 * @author sutting
 *
 */
public class MeaningGatherer {
	
	Logger logger = Logger.getLogger(MeaningGatherer.class);
	
	HtmlCrowler crowler = null;
	int maxMeaningNum = 3;
	
	public MeaningGatherer(){
		crowler = new HtmlCrowler();
	}
	
	public String getImageUrl(String word) throws IOException{
		//String url = "http://images.google.co.kr/images?imgtbs=zt&gbv=2&hl=ko&tbo=1&newwindow=1&tbs=isch:1&q="+word+"&ei=AxBcS8uIM4XgtgPvypSaAg&sa=X&oi=tool&resnum=1&ct=tlink&ved=0CAoQpwU#start=0&imgtbs=tz&tbs=isch:1&imgsz=m&imgtype=clipart&tbo=1";
		String url = "http://images.google.co.kr/images?q=nice";
		String result = "";
		
		System.out.println("call Url : "+url);
		String resultHtml = crowler.getHtml(url);
		HtmlCleaner cleaner = new HtmlCleaner();
		TagNode node = cleaner.clean(resultHtml);
		TagNode[] myNodes = node.getElementsByAttValue("id", "tDataImage0", true, true);
		System.out.println(myNodes.length);
		
		return result;
	}
	
	public String getSoundSymbol(TagNode node) throws IOException{
		String result = "";
		TagNode[] myNodes = node.getElementsByAttValue("class", "dct-tp", true, true);
		for(int i = 0 ; i < myNodes.length ; i++)
		{
			//System.out.println(myNodes[i].getText());
			//if(!myNodes[i].getText().equals(""))
			if(i == 1)
				result = myNodes[i].getText().toString();
		}
		return result;
	}
	
	public String getSoundTag(TagNode node) throws IOException{
		HtmlCleaner cleaner = new HtmlCleaner();
		CleanerProperties props = cleaner.getProperties(); // cleaner의 속성을 정의하기 위한.. 변수
		SimpleXmlSerializer se = new SimpleXmlSerializer(props);
		StringBuffer sb = new StringBuffer();

		Writer writer = new StringWriter();
		String result = null;//new Text("");
		
		TagNode[] myNodes = node.getElementsByAttValue("class", "prn-btn", true, true);
		for (int i = 0; i < myNodes.length; i++) {
			try {
				if(i == 0)
					se.writeXml(myNodes[i], writer, "UTF-8"); // 그냥 화면으로 출력됩니다.
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//얻어낸 소리 플래쉬 태그에 절대 경로 http://www.google.co.kr 붙이기
		String[] resultPart = writer.toString().split("\n");
		if(resultPart.length > 1){
			String resultStr = writer.toString().split("\n")[1];
			resultStr = resultStr.replace("\"/dictionary", "\"http://www.google.co.kr/dictionary");
			result = resultStr;
			//System.out.println("soundTag: "+result.toString());
		}
		return result;
	}
	
	public String makeGoogleUrl(String wordStr){
		String wordStrReplaced = wordStr.replace(" ", "+");
		String url = "http://www.google.co.kr/dictionary?langpair=en|ko&q="+wordStrReplaced+"&hl=ko&aq=f";
		return url;
	}
	
	public VBWord analysisWord(String wordStr) throws IOException{
		return analysisWord(wordStr, true);
	}
	
	public VBWord analysisWord(String wordStr, boolean isNew) throws IOException{
		VBWord word = new VBWord();
		//wordStr = wordStr.replace("+", " ");
		//String wordStrReplaced = wordStr.replace(" ", "+");
		String url = makeGoogleUrl(wordStr);
		System.out.println("call Url : "+url);
		String resultHtml = crowler.getHtml(url);
		//System.out.println(resultHtml);
		HtmlCleaner cleaner = new HtmlCleaner();
		TagNode node = cleaner.clean(resultHtml);
		
		List<VBWordInfo> wordInfoList = getMeaning(node);
		String soundTag = getSoundTag(node);
		String soundSymbol = getSoundSymbol(node);
		
		//단어를 못찾은 경우, 추천 단어를 한번 더 고려함
		if(wordInfoList == null || wordInfoList.size() == 0){
			System.out.println("couldn't find meaning information.");
			
			String correctedWord = getCorrectedWord(node);
			//추천 단어를 찾았다면,
			if(!"n/a".equals(correctedWord)){
				System.out.println("correctedWord: "+correctedWord);
				wordStr = correctedWord;
				url = makeGoogleUrl(correctedWord);
				System.out.println("call second Url : "+url);
				String secondResultHtml = crowler.getHtml(url);
				TagNode secondNode = cleaner.clean(secondResultHtml);
				wordInfoList = getMeaning(secondNode);
				soundTag = getSoundTag(secondNode);
				soundSymbol = getSoundSymbol(secondNode);
			}else{
				//뜻을 못찾은 경우
				return null;
			}
		}
		
		
		word.setWordName(wordStr);
		word.setSoundHtml(soundTag);
		word.setSoundSymbol(soundSymbol);
		return word;
	}
	
	public String getCorrectedWord(TagNode node) throws IOException{
		TagNode[] myNodes = node.getElementsByAttValue("class", "err", true, true);
		String result = "n/a";
		System.out.println(myNodes.length);
		if(myNodes.length > 0)
		{
			TagNode[] subNodes = myNodes[0].getElementsByAttValue("class", "p", true, true);
			if(subNodes.length == 0)
				return result;
			result = subNodes[0].getAttributeByName("href");
			String[] resultPart = result.split("&");
			resultPart = resultPart[0].split("q=");
			if(resultPart.length > 0)
				result = resultPart[1];
		}
		return result;
	}
	public List<VBWordInfo> getMeaning(TagNode node) throws IOException{
		TagNode[] ulNodes = node.getElementsByName("ul", true);
		if(ulNodes == null || ulNodes.length == 0){
			return null;
		}
		TagNode[] divNodes = ulNodes[0].getElementsByName("div", true);
		Vector<VBWordInfo> resultVector = new Vector<VBWordInfo>();
		
		int insertedMeaningNum = 0;
		
		for(int k = 0 ; k < divNodes.length ; k++){
			if(!(insertedMeaningNum < maxMeaningNum || maxMeaningNum == 0 ))
				break;
			
			if("dct-em".equals(divNodes[k].getAttributeByName("class"))){
				TagNode[] myNodes = divNodes[k].getElementsByAttValue("class", "dct-tt", true, true);
				//의미
				for(int i = 0 ; i < myNodes.length ; i++){
					insertedMeaningNum++;
					String meaning = myNodes[i].getText().toString().trim();
					//System.out.println("::originalMeaning: "+meaning);
					//단어시험 용도로 간소화 시킴
					String shortMeaning = meaning;
					//System.out.println("::BEFORE: "+shortMeaning);
					shortMeaning = shortMeaning.replaceAll("【[^【^】]*】", "");
					//System.out.println("::BEFORE1: "+shortMeaning);
					shortMeaning = shortMeaning.replaceAll("〈[^〈^〉]*〉", "");
					//System.out.println("::BEFORE2: "+shortMeaning);
					shortMeaning = shortMeaning.replaceAll("《[^》^《]*》", "");
					//System.out.println("::BEFORE3: "+shortMeaning);
					shortMeaning = shortMeaning.replaceAll("\\[[^\\]^\\[]*\\]", "");
					//System.out.println("::BEFORE4: "+shortMeaning);
					shortMeaning = shortMeaning.replaceAll("\\([^\\)^\\(]*\\)", "");
					
					//System.out.println("::splitStr: "+shortMeaning);
					String[] meaningPart = shortMeaning.split(",");
					shortMeaning = meaningPart[0];
					//Countable or Uncountable 걸러냄 i.e. U,N 뜻.. 
					if(shortMeaning.length() == 1) shortMeaning+=","+meaningPart[1];

					
					//괄호로 둘러쌓인 부분 미리 저장(맨 앞부분에 나온것만)
//					String parenPart = "";
//					if(shortMeaning.indexOf(")")>-1 && shortMeaning.indexOf("(")>-1)
//						parenPart = shortMeaning.substring(shortMeaning.indexOf("("), shortMeaning.indexOf(")")+1);
//					
//					//괄호 부분 제거후,
//					shortMeaning = shortMeaning.replaceAll("\\([^\\)^\\(]*\\)", "");
//					//세미콜론으로 split 후 첫번째 것만 사용
//					shortMeaning = shortMeaning.split(";")[0];
//					//미리저장한 괄호 앞부분을 붙임
//					shortMeaning = parenPart + shortMeaning;
					
					//System.out.println("max :"+maxMeaningNum+", "+i+", "+myNodes[i].getText().toString().trim());
					VBWordInfo wi = new VBWordInfo();
					//wi.setCategory("N/A");
					wi.setMeaning(meaning);
					wi.setShortMeaning(shortMeaning);
					resultVector.add(wi);
				}
			}
		}
		return resultVector;
	}
	
	public static void main(String[] argv) throws IOException{
		//Text test = new Text("aa");
		
		//System.out.println(test.getValue());
		//MeaningGatherer mg = new MeaningGatherer();
		//VBWord result = mg.analysisWord(null, "testify");
		
		//Pattern p = Pattern.compile("\\(.*\\)");
		//Matcher m = p.matcher("(abc;defg).;...kkk(ljlklk)");
		
		String test = "(....bbbb.c)ccbsdf";
		System.out.println(test.indexOf(")"));
		System.out.println(test.substring(test.indexOf("("), test.indexOf(")")+1));
		
		System.out.println("《a》 화장실 《lavatory의 완곡한 말》");
		//if (m.find()) {
		    //System.out.println(m.group(0));
		//}
		
//		String input = "(abcdefg)....kkk";
//		Matcher matcher = Pattern.compile("[^0-9]+([0-9]+)[^0-9]+").matcher(input);
//		if (matcher.find()) {
//		    String someNumberStr = matcher.group(1);
//		    // if you need this to be an int:
//		    int someNumberInt = Integer.parseInt(someNumberStr);
//		}
		
		//List<VBWordInfo> wordInfoList = result.getWordInfoList();
//		for(int i = 0 ; i < wordInfoList.size() ; i++)
//		{
//			System.out.println(wordInfoList.get(i).getMeaning());
//		}
		//System.out.println(result.getSoundTag());
		//List<VBWordInfo> result = mg.getMeaning("testify");
		//for(int i = 0 ; i < result.size() ; i++)
		//{
		//	System.out.println(result.get(i).getShortMeaning());
		//}
		
		
	}
}
