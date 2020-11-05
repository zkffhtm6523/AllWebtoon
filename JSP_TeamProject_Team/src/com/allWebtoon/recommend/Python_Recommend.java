package com.allWebtoon.recommend;

import java.util.ArrayList;
import java.util.List;

import com.allWebtoon.dao.WebtoonListDAO;
import com.allWebtoon.vo.WebtoonVO;

import jep.Jep;
import jep.JepConfig;
import jep.JepException;

public class Python_Recommend {
	public static void main(String[] args) {
		
		 ////파이썬호출
		 try {
			List<WebtoonVO> list = new ArrayList<WebtoonVO>();
			JepConfig jepConfig = new JepConfig().addSharedModules("numpy")
                   .addSharedModules("pandas")
                   .addSharedModules("scipy") 
                   .addSharedModules("tensorflow")
                   .addSharedModules("sklearn");
            Jep jep = new Jep(jepConfig);
            
            int w_no = 2;
            
			jep.set("w_no_args", w_no);
			jep.runScript("/Users/hyeseon/python_test/Python/recommend_toon.py");
			
			List arr = (List) jep.getValue("recomment_result");
			
			for(int i=0; i<arr.size(); i++) {
				String rec_wno = ((List) arr.get(i)).get(0).toString();
				list.add(WebtoonListDAO.selrecommendWebtoon(Integer.parseInt(rec_wno)));
			}
			
			for(WebtoonVO s : list) {
				System.out.println(s.getW_no());
				System.out.println(s.getW_title());
				System.out.println(s.getW_thumbnail());
			}
			
			jep.close();
			
			//request.setAttribute("rec_list", list);
			
		 } catch (JepException e) {
			System.out.println("추천작품이 없습니다.");
		 }
	
	}
}
