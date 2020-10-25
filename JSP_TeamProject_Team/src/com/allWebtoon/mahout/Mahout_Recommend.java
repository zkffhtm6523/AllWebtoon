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
			UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, dataModel);
			// 추천 엔진 생성
			Recommender recommender = new GenericUserBasedRecommender(dataModel, neighborhood, similarity);
			// 추천을 배열에 담음
			List<RecommendedItem> recommendations = recommender.recommend(u_no, getList_length);
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
			List<Preference> prefsList = Lists.newArrayList();
			for (int j = 0; j < webtoonList.get(i).getW_list().size(); j++) {
				prefsList.add(new GenericPreference(
						webtoonList.get(i).getU_no(),
						webtoonList.get(i).getW_list().get(j).getW_no(),
						(float) webtoonList.get(i).getW_list().get(j).getC_rating())
				);
			}
			result.put(webtoonList.get(i).getU_no(), new GenericUserPreferenceArray(prefsList));
		}
		return new GenericDataModel(result);
	}
}
