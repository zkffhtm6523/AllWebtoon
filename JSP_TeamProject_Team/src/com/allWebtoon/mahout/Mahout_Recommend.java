package com.allWebtoon.mahout;

import java.util.ArrayList;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import com.allWebtoon.dao.WebtoonListDAO;
import com.allWebtoon.vo.WebtoonCmtDomain;
import com.allWebtoon.vo.WebtoonCmtVO;
import com.allWebtoon.vo.WebtoonVO;
import com.google.common.collect.Lists;

public class Mahout_Recommend {
	public static List<WebtoonVO> getRecommendList(GenericDataModel dataModel, int u_no, int getList_length){
		List<WebtoonVO> recommendList = new ArrayList<WebtoonVO>();
		try {
			// u_no | w_no | c_rating
			UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);
			// 유사 이웃 5명 선택
			System.out.println("datamodel maxPreference : "+dataModel.getMaxPreference());
			System.out.println("datamodel minPreference : "+dataModel.getMinPreference());
			System.out.println("datamodel numitem : "+dataModel.getNumItems());
			System.out.println("similarity : "+similarity);
			UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, dataModel);
			// 추천 엔진 생성
			Recommender recommender = new GenericUserBasedRecommender(dataModel, neighborhood, similarity);
			// 추천을 배열에 담음
			System.out.println("recommend 전 u_no : "+u_no+", leng : "+getList_length );
			List<RecommendedItem> recommendations = recommender.recommend(u_no, getList_length);
			System.out.println("recommendation 출력 전 size : "+recommendations.size());
			for (RecommendedItem recommendedItem : recommendations) {
				recommendList.add(WebtoonListDAO.selrecommendWebtoon(
						(int) recommendedItem.getItemID()));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
			return recommendList;
		}
	//2차원 배열 필요([u_no, [w_no][c_rating])
	public static GenericDataModel parsingDataModel(List<WebtoonCmtDomain> webtoonList) {
		FastByIDMap<PreferenceArray> result = new FastByIDMap<PreferenceArray>();
		for (int i = 0; i < webtoonList.size(); i++) {
			System.out.println("--------u_no : "+webtoonList.get(i).getU_no()+"--------");
			List<Preference> prefsList = Lists.newArrayList();
			for (int j = 0; j < webtoonList.get(i).getW_list().size(); j++) {
				prefsList.add(new GenericPreference(
						webtoonList.get(i).getU_no(),
						webtoonList.get(i).getW_list().get(j).getW_no(),
						webtoonList.get(i).getW_list().get(j).getC_rating())
				);
				System.out.print("w_no : "+webtoonList.get(i).getW_list().get(j).getW_no());
				System.out.println("/ c_crating : "+webtoonList.get(i).getW_list().get(j).getC_rating());
			}
			result.put(webtoonList.get(i).getU_no(), new GenericUserPreferenceArray(prefsList));
		}
		return new GenericDataModel(result);
	}
}
