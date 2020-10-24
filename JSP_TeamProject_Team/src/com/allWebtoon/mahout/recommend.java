package com.allWebtoon.mahout;

import java.util.List;

import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import com.google.common.collect.Lists;

public class recommend {
	public static class ItemRecommend {
		public static void main(String[] args) {
			try {
				//user id | item id | rating | timestamp.
				//웹툰 평점 넣으면 됨
//				TanimotoCoefficientSimilarity similarity = new TanimotoCoefficientSimilarity(text());
//				GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(text(), similarity);
//				System.out.println((float)0.2);
//				for (LongPrimitiveIterator items = text().getItemIDs(); items.hasNext();) {
//					long itemId = items.nextLong();
//																					//itemID, 연관 추천 5개 출력
//					List<RecommendedItem> recommendations = recommender.mostSimilarItems(itemId, 5);
//					List<RecommendedItem> recommendations2 = recommender.recommend(1645390, 5);
//					for (RecommendedItem recommendation : recommendations) {
//						System.out.println("itemId : "+itemId + ", getItemID : " + recommendation.getItemID()
//						+ ", 유사성 : " + recommendation.getValue());
//					}
//					System.out.println();
//				}
				UserSimilarity similarity = new PearsonCorrelationSimilarity(text());
				
				//유사 이웃 2명 선택  
				UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, text());
				//추천 엔진 생성  
				Recommender recommender = new GenericUserBasedRecommender(text(), neighborhood, similarity);
				
				//사용자 1645390에게 웹툰 3개 추천  
				
				List<RecommendedItem> recommendations = recommender.recommend(3, 5);
				
				for (RecommendedItem recommendation : recommendations) {
					System.out.println("item : "+recommendation);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 

		}
		//데이터 모델 만드는 거
		public static GenericDataModel text() {
			//새로운 배열
			FastByIDMap<PreferenceArray> result = new FastByIDMap<PreferenceArray>();
			List<Preference> prefsList0 = Lists.newArrayList();
			prefsList0.add(new GenericPreference(1, 101, (float) 5.0));
			prefsList0.add(new GenericPreference(1, 102, (float) 3.0));
			prefsList0.add(new GenericPreference(1, 103, (float) 2.5));
			result.put(1, new GenericUserPreferenceArray(prefsList0));
			List<Preference> prefsList1 = Lists.newArrayList();
			prefsList1.add(new GenericPreference(2, 101, (float) 2.0));
			prefsList1.add(new GenericPreference(2, 102, (float) 2.5));
			prefsList1.add(new GenericPreference(2, 103, (float) 5.0));
			prefsList1.add(new GenericPreference(2, 104, (float) 2.0));
			//내가 평가한 거 중에서 유사한 거 찾는거 ? 
			result.put(2, new GenericUserPreferenceArray(prefsList1));
			List<Preference> prefsList2 = Lists.newArrayList();
			prefsList2.add(new GenericPreference(3, 101, (float) 2.5));
			prefsList2.add(new GenericPreference(3, 104, (float) 4.0));
			prefsList2.add(new GenericPreference(3, 105, (float) 4.5));
			prefsList2.add(new GenericPreference(3, 107, (float) 5.0));
			result.put(3, new GenericUserPreferenceArray(prefsList2));
			List<Preference> prefsList3 = Lists.newArrayList();
			prefsList3.add(new GenericPreference(4, 101, (float) 5.0));
			prefsList3.add(new GenericPreference(4, 103, (float) 3.0));
			prefsList3.add(new GenericPreference(4, 104, (float) 4.5));
			prefsList3.add(new GenericPreference(4, 106, (float) 4.0));
			result.put(4, new GenericUserPreferenceArray(prefsList3));
			List<Preference> prefsList4 = Lists.newArrayList();
			prefsList4.add(new GenericPreference(5, 101, (float) 4.0));
			prefsList4.add(new GenericPreference(5, 102, (float) 3.0));
			prefsList4.add(new GenericPreference(5, 103, (float) 2.0));
			prefsList4.add(new GenericPreference(5, 104, (float) 4.0));
			prefsList4.add(new GenericPreference(5, 105, (float) 3.5));
			prefsList4.add(new GenericPreference(5, 106, (float) 4.0));
			result.put(5, new GenericUserPreferenceArray(prefsList4));
			return new GenericDataModel(result);
		}
		public static DataModel getDataModel(long[] userIDs, Double[][] prefValues) {
			 FastByIDMap<PreferenceArray> result = new FastByIDMap<PreferenceArray>();
			 
			 for (int i = 0; i < userIDs.length; i++) {
			  List<Preference> prefsList = Lists.newArrayList();
			  
			  for (int j = 0; j < prefValues[i].length; j++) {
			   if (prefValues[i][j] != null) {
			    prefsList.add(new GenericPreference(userIDs[i], j, prefValues[i][j].floatValue()));
			   }
			  }
			  
			  if (!prefsList.isEmpty()) {
			   result.put(userIDs[i], new GenericUserPreferenceArray(prefsList));
			  }
			  
			 }
			 return new GenericDataModel(result);
			}
	}
}
