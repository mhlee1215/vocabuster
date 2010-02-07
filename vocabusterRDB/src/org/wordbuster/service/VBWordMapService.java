package org.wordbuster.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.wordbuster.dao.VBWordMapDAO;
import org.wordbuster.domain.VBWordMap;

public class VBWordMapService {
	
	@Autowired
	private VBWordMapDAO wordMapDAO;
	
	public List<VBWordMap> retrieveWordMapListAll(){
		return wordMapDAO.retrieveUserWordMap("");
	}
	
	public boolean deleteWordMapAll(){
		return wordMapDAO.deleteWordMap("", "");
	}
}
