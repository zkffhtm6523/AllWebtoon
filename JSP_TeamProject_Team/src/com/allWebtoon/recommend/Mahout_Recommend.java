package com.allWebtoon.recommend;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.common.Weighting;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.JDBCDataModel;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.IDRescorer;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import com.allWebtoon.dao.WebtoonListDAO;
import com.allWebtoon.vo.WebtoonCmtDomain;
import com.allWebtoon.vo.WebtoonCmtVO;
import com.allWebtoon.vo.WebtoonVO;
import com.google.common.collect.Lists;
import com.mysql.cj.jdbc.MysqlDataSource;

public class Mahout_Recommend {
	/*public static List<WebtoonVO> getRecommendList(GenericDataModel dataModel, int u_no, int getList_length){
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
				System.out.println("recommecdedItem : " + recommendedItem);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
			return recommendList;
		}*/
	
	
	//ItemBasedRecommend
	public static List<WebtoonVO> getRecommendList(DataModel dataModel, int w_no, int u_no, int getList_length){
		List<WebtoonVO> recommendList = new ArrayList<WebtoonVO>();
		try {
			//피어슨 상관관계 : 사람들이 준 평점을 분석해 아이템 간의 유사도를 측정해줌. + 가중치 
			ItemSimilarity itemSimilarity = new PearsonCorrelationSimilarity(dataModel, Weighting.WEIGHTED);
			
			
			//타니모토 계수 : 나와 평가한 작품의 교집합이 큰 사용자가 평가한 작품 중,내가 보지 않은 것을 추천. (평가한 작품의 교집합을 찾지만 별점이 반영되지는 않을듯. ) : 0~1 
			//TanimotoCoefficientSimilarity itemSimilarity = new TanimotoCoefficientSimilarity(dataModel);					
			
			//로그우도 유사도 : 타니모토계수와 유사. 선호도 고려하지 않고 공통된 아이템 수 활용.
			//ItemSimilarity itemSimilarity = new LogLikelihoodSimilarity(dataModel);		
			
			ItemBasedRecommender recommender = new GenericItemBasedRecommender(dataModel, itemSimilarity);
			
			long itemId = (long)w_no;
			//long itemId = 2;
		    
			String input_genre = WebtoonListDAO.webtoonDetail((int)itemId, u_no).getGenre_name();		
			String[] input_genres = input_genre.split(", ");											//선택한 작품의 장르배열
			
			
			List<RecommendedItem> recommendations = recommender.mostSimilarItems(itemId, getList_length+20);
			
			double[][] result = new double[recommendations.size()][2];
		    for(RecommendedItem recommendation : recommendations){
		    	
		    	double cor = recommendation.getValue();						//cor: 기존 유사도에 장르 가중치를 포함할 유사도 
		    	String genre = WebtoonListDAO.webtoonDetail((int)recommendation.getItemID(), u_no).getGenre_name();
		    	String[] genres = genre.split(", ");						//각 작품의 장르 배열 
		    	
		    	for(String input_g : input_genres) {
		    		if(Arrays.asList(genres).contains(input_g)) {			//genres 배열 안에 input_g 가 있다면 (같은 장르라면) 
		    			cor += 0.1;											//0.1씩 가중치 올림
		    		}
		    	}
		    	
		    	result[recommendations.indexOf(recommendation)][0] = recommendation.getItemID();
		    	result[recommendations.indexOf(recommendation)][1] = cor;
		    	
			    //recommendList.add(WebtoonListDAO.selrecommendWebtoon((int) recommendation.getItemID()));
		    	
			    //System.out.println(itemId + "," + recommendation.getItemID() + " , " + recommendation.getValue() + " , 가중치 적용: " + cor) ;
		    }
		    
		    //result 배열 유사도 기준 내림차순 정렬 
		    Arrays.sort(result,(o2,o1) -> {
		    	return Double.compare(o1[1], o2[1]);
		    });

		    for(int i=0; i<result.length; i++) {
		    	if(i < getList_length && result[i][1] > 0) {				//현재 recommendList에 들어간 갯수가 getList_length보다 작고 유사도가 0보다 큰 경우에만 추가. 
		    		recommendList.add(WebtoonListDAO.selrecommendWebtoon((int)result[i][0]));
		    		System.out.println("result : "  + result[i][0] + " , " + result[i][1]);
		    	}
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
	//Mysql에서 테이블 가져와서 DataModel로 변
	public static DataModel parsingDataModel() throws SQLException {
		
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setServerName("localhost");
		dataSource.setUser("root");
		dataSource.setPassword("root");
		dataSource.setDatabaseName("webtoon_db");
		dataSource.setServerTimezone("UTC");
		DataModel model = new MySQLJDBCDataModel(dataSource,
				"t_comment", "u_no", "w_no", "c_rating", null);

		return model;
	}
}
