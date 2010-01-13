package org.wordbuster.util;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.wordbuster.domain.wordInfo;

public class MeaningGatherer {
	HtmlCrowler crowler = null;
	int maxMeaningNum = 3;
	
	public MeaningGatherer(){
		crowler = new HtmlCrowler();
	}
	
	public List<wordInfo> getMeaning(String word) throws IOException{
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
		Vector<wordInfo> resultVector = new Vector<wordInfo>();
		
		for(int k = 0 ; k < divNodes.length ; k++){
			if("dct-em".equals(divNodes[k].getAttributeByName("class"))){
				TagNode[] myNodes = divNodes[k].getElementsByAttValue("class", "dct-tt", true, true);
				for(int i = 0 ; i < myNodes.length && (i < maxMeaningNum || maxMeaningNum == 0 ) ; i++){
					String meaning = myNodes[i].getText().toString().trim();
					//System.out.println(i+", "+myNodes[i].getText().toString().trim());
					wordInfo wi = new wordInfo();
					wi.setCategory("");
					wi.setMeaning(meaning);
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
