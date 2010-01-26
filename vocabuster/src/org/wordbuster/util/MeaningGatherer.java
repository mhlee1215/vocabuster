package org.wordbuster.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.Vector;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.SimpleXmlSerializer;
import org.htmlcleaner.TagNode;
import org.wordbuster.domain.VBWord;
import org.wordbuster.domain.VBWordInfo;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;

/**
 * Designed for Google dictionary
 * @author sutting
 *
 */
public class MeaningGatherer {
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
	
	public Text getSoundTag(TagNode node) throws IOException{
		HtmlCleaner cleaner = new HtmlCleaner();
		CleanerProperties props = cleaner.getProperties(); // cleaner의 속성을 정의하기 위한.. 변수
		SimpleXmlSerializer se = new SimpleXmlSerializer(props);
		StringBuffer sb = new StringBuffer();

		Writer writer = new StringWriter();
		Text result = null;//new Text("");
		
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
			result = new Text(resultStr);
			System.out.println("soundTag: "+result.toString());
		}
		return result;
	}
	
	public VBWord analysisWord(String wordStr) throws IOException{
		VBWord word = new VBWord();
		Key wordKey = KeyFactory.createKey(VBWord.class.getSimpleName(), wordStr);
		word.setKey(wordKey);
		return analysisWord(word, wordStr);
	}
	
	public String makeGoogleUrl(String wordStr){
		String wordStrReplaced = wordStr.replace(" ", "+");
		String url = "http://www.google.co.kr/dictionary?langpair=en|ko&q="+wordStrReplaced+"&hl=ko&aq=f";
		return url;
	}
	
	public VBWord analysisWord(VBWord word, String wordStr) throws IOException{
		//wordStr = wordStr.replace("+", " ");
		//String wordStrReplaced = wordStr.replace(" ", "+");
		String url = makeGoogleUrl(wordStr);
		System.out.println("call Url : "+url);
		String resultHtml = crowler.getHtml(url);
		//System.out.println(resultHtml);
		HtmlCleaner cleaner = new HtmlCleaner();
		TagNode node = cleaner.clean(resultHtml);
		
		List<VBWordInfo> wordInfoList = getMeaning(node);
		Text soundTag = getSoundTag(node);
		String soundSymbol = getSoundSymbol(node);
		
		//단어를 못찾은 경우, 추천 단어를 한번 더 고려함
		if(wordInfoList.size() == 0){
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
		
		if(word != null){
			word.setWordName(wordStr);
			word.setWordInfoList(wordInfoList);
			word.setSoundHtml(soundTag);
			word.setSoundSymbol(soundSymbol);
			word.setInsertedCount(0);
		}
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
					String[] meaningPart = meaning.split(",");
					String shortMeaning = meaningPart[0];
					if(shortMeaning.length() == 1) shortMeaning+=","+meaningPart[1];
					shortMeaning = shortMeaning.replaceAll("【.*】", "");
					shortMeaning = shortMeaning.replaceAll("〈.*〉", "");
					shortMeaning = shortMeaning.replaceAll("《.*》", "");
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
		MeaningGatherer mg = new MeaningGatherer();
		VBWord result = mg.analysisWord(null, "testify");
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
