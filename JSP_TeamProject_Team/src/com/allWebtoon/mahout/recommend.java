package com.allWebtoon.mahout;

import java.util.List;

import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import com.google.common.collect.Lists;

public class recommend {
	public static class ItemRecommend {
		public static void main(String[] args) {
			try {
				//user id | item id | rating | timestamp.
				//웹툰 평점 넣으면 됨
				TanimotoCoefficientSimilarity similarity = new TanimotoCoefficientSimilarity(text());
				GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(text(), similarity);
				System.out.println((float)0.2);
				for (LongPrimitiveIterator items = text().getItemIDs(); items.hasNext();) {
					long itemId = items.nextLong();
																					//itemID, 연관 추천 5개 출력
					List<RecommendedItem> recommendations = recommender.mostSimilarItems(itemId, 5);
					for (RecommendedItem recommendation : recommendations) {
						System.out.println("itemId : "+itemId + ", itemId와 유사한 것 추천 : " + recommendation.getItemID()
						+ ", 유사성 : " + recommendation.getValue());
					}
					System.out.println();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 

		}
		//데이터 모델 만드는 거
		public static GenericDataModel text() {
			//새로운 배열
			FastByIDMap<PreferenceArray> result = new FastByIDMap<PreferenceArray>();
			List<Preference> prefsList = Lists.newArrayList();
			prefsList.add(new GenericPreference(1645390, 123456, (float) 4.0));
			prefsList.add(new GenericPreference(1645390, 123457, (float) 3.0));
			prefsList.add(new GenericPreference(1645390, 123458, (float) 6.0));
			prefsList.add(new GenericPreference(1645390, 123459, (float) 8.0));
			prefsList.add(new GenericPreference(1645390, 123460, (float) 5.0));
			prefsList.add(new GenericPreference(1645390, 123461, (float) 6.0));
			prefsList.add(new GenericPreference(1645390, 123462, (float) 7.0));
			prefsList.add(new GenericPreference(1645390, 123463, (float) 8.0));
			prefsList.add(new GenericPreference(1645390, 123464, (float) 9.0));
			prefsList.add(new GenericPreference(1645390, 123465, (float) 10.0));
			prefsList.add(new GenericPreference(1645390, 123466, (float) 11.0));
			prefsList.add(new GenericPreference(1645390, 123467, (float) 12.0));
			prefsList.add(new GenericPreference(1645390, 123468, (float) 13.0));
			result.put(1645390, new GenericUserPreferenceArray(prefsList));
			List<Preference> prefsList1 = Lists.newArrayList();
			prefsList1.add(new GenericPreference(1645391, 123456, (float) 4.0));
			prefsList1.add(new GenericPreference(1645391, 12227, (float) 3.0));
			prefsList1.add(new GenericPreference(1645391, 123789, (float) 2.0));
			prefsList1.add(new GenericPreference(1645391, 1359, (float) 2.0));
			prefsList1.add(new GenericPreference(1645391, 1245, (float) 0));
			result.put(1645391, new GenericUserPreferenceArray(prefsList1));
			List<Preference> prefsList2 = Lists.newArrayList();
			prefsList2.add(new GenericPreference(1645392, 123456, (float) 4.0));
			prefsList2.add(new GenericPreference(1645392, 123123457, (float) 3.0));
			prefsList2.add(new GenericPreference(1645392, 123414458, (float) 3.0));
			prefsList2.add(new GenericPreference(1645392, 123455989, (float) 1.0));
			prefsList2.add(new GenericPreference(1645392, 123465160, (float) 0.5));
			result.put(1645392, new GenericUserPreferenceArray(prefsList2));
			List<Preference> prefsList3 = Lists.newArrayList();
			prefsList3.add(new GenericPreference(1645393, 123456, (float) 4.0));
			prefsList3.add(new GenericPreference(1645393, 123457, (float) 3.0));
			prefsList3.add(new GenericPreference(1645393, 123458, (float) 2.0));
			prefsList3.add(new GenericPreference(1645393, 123459, (float) 2.0));
			prefsList3.add(new GenericPreference(1645393, 123460, (float) 2.0));
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
