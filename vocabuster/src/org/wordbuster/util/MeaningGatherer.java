package org.wordbuster.util;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.wordbuster.domain.VBWordInfo;

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
	
	public List<VBWordInfo> getMeaning(String word) throws IOException{
		String url = "http://www.google.co.kr/dictionary?langpair=en|ko&q="+word+"&hl=ko&aq=f";
		System.out.println("call Url : "+url);
		String resultHtml = crowler.getHtml(url);
		//System.out.println(resultHtml);
		HtmlCleaner cleaner = new HtmlCleaner();
		TagNode node = cleaner.clean(resultHtml);
		//System.out.println(resultHtml);
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
					String shortMeaning = meaning.split(",")[0];
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
		MeaningGatherer mg = new MeaningGatherer();
		mg.getMeaning("testify");
		
		
	}
}
