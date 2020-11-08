package com.allWebtoon.recommend;

import java.util.ArrayList;
import java.util.List;

import com.allWebtoon.dao.WebtoonListDAO;
import com.allWebtoon.vo.WebtoonVO;

public class Python_Recommend {
//	@SuppressWarnings("deprecation")
//	public static List<WebtoonVO> getRecommendList(int w_no){
//		List<WebtoonVO> list = new ArrayList<WebtoonVO>();
//		 try {
//			JepConfig jepConfig = new JepConfig().addSharedModules("numpy")
//                   .addSharedModules("pandas")
//                   .addSharedModules("scipy") 
//                   .addSharedModules("tensorflow")
//                   .addSharedModules("sklearn");
//			
//			Jep jep = new Jep(jepConfig);
//            
//			jep.set("w_no_args", w_no);
//			jep.runScript("D:\\recommend_toon.py");
//			System.out.println("renScript 이후");
//			List arr = (List) jep.getValue("recomment_result");
//			
//			for(int i=0; i<arr.size(); i++) {
//				String rec_wno = ((List) arr.get(i)).get(0).toString();
//				list.add(WebtoonListDAO.selrecommendWebtoon(Integer.parseInt(rec_wno)));
//			}
//			jep.close();
//		 } catch (JepException e) {
//			System.out.println("추천작품이 없습니다.");
//		 }
//		 return list;
//	}
}
