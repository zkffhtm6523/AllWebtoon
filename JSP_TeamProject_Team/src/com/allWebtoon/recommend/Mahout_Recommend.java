package com.allWebtoon.recommend;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.common.Weighting;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.SpearmanCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import com.allWebtoon.dao.WebtoonListDAO;
import com.allWebtoon.vo.WebtoonCmtDomain;
import com.allWebtoon.vo.WebtoonVO;
import com.google.common.collect.Lists;
import com.mysql.cj.jdbc.MysqlDataSource;

public class Mahout_Recommend {
	public static List<WebtoonVO> getUserBasedRecommend(DataModel model, int u_no, int getList_length){
		List<WebtoonVO> recommendList = new ArrayList<WebtoonVO>();
        //유저 기준 유사성
		UserSimilarity similarity;
		try {
			//타니모토 계수
			//similarity = new TanimotoCoefficientSimilarity(model);
			//스피어만 계수
			similarity = new SpearmanCorrelationSimilarity(model);
			//유클리디언 거리
			//similarity = new EuclideanDistanceSimilarity(model);
			System.out.println("loginUser.u_no : "+u_no);
			long recU_no = (long)u_no;
	        //나와 비슷한 성향을 가진(같은 작품에 추천을 한 10명을 참고)
	        UserNeighborhood neighborhood = new NearestNUserNeighborhood(10, similarity, model);
	        //유저 기준 추천 모델
	        UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
	
	        //u_no에게 getList_length개 아이템 추천
	        List<RecommendedItem> recommendations = null;
			recommendations = recommender.recommend(recU_no, getList_length);
			//유사도 기준 정렬 위한 임시 배열
			double[][] sortingTempArr = new double[recommendations.size()][2];
			
			for (RecommendedItem recommendation : recommendations) {
				double cor = recommendation.getValue();	
				sortingTempArr[recommendations.indexOf(recommendation)][0] = recommendation.getItemID();
				sortingTempArr[recommendations.indexOf(recommendation)][1] = cor;
			}
			//result 배열 유사도 기준 내림차순 정렬 
		    Arrays.sort(sortingTempArr,(o2,o1) -> {
		    	return Double.compare(o1[1], o2[1]);
		    });
		    //유사도 기준 정렬 끝난 웹툰 -> 웹툰 DB 정보 가져오기
		    for (int i = 0; i < sortingTempArr.length; i++) {
		    	recommendList.add(WebtoonListDAO.selrecommendWebtoon((int)sortingTempArr[i][0]));
			}
		} catch (TasteException e) {
			System.out.println("MyPage 추천 시스템 에러");
			e.printStackTrace();
		}
			return recommendList;
	}
	//ItemBasedRecommend
	public static List<WebtoonVO> getRecommendList(DataModel dataModel, int w_no, int u_no, int getList_length){
		List<WebtoonVO> recommendList = new ArrayList<WebtoonVO>();
		
		try {
			
			long itemId = (long)w_no;
			
			//피어슨 상관관계 : 사람들이 준 평점을 분석해 아이템 간의 유사도를 측정해줌. + 가중치 
			ItemSimilarity itemSimilarity = new PearsonCorrelationSimilarity(dataModel, Weighting.WEIGHTED);
			System.out.println("피어슨 상관관계 : "+itemSimilarity);
			//타니모토 계수 : 나와 평가한 작품의 교집합이 큰 사용자가 평가한 작품 중,내가 보지 않은 것을 추천. 
			//(평가한 작품의 교집합을 찾지만 별점이 반영되지는 않는다) : 0~1 
			//TanimotoCoefficientSimilarity itemSimilarity = new TanimotoCoefficientSimilarity(dataModel);					
			
			//로그우도 유사도 : 타니모토계수와 유사. 선호도 고려하지 않고 공통된 아이템 수 활용.
			//ItemSimilarity itemSimilarity = new LogLikelihoodSimilarity(dataModel);		
			
			ItemBasedRecommender recommender = new GenericItemBasedRecommender(dataModel, itemSimilarity);
			List<RecommendedItem> recommendations = recommender.mostSimilarItems(itemId, getList_length+16);
			
			
			String input_genre = WebtoonListDAO.webtoonDetail((int)itemId, u_no).getGenre_name();
			//선택한 작품의 장르배열
			String[] input_genres = input_genre.split(", ");											
			
			double[][] result = new double[recommendations.size()][2];
		    for(RecommendedItem recommendation : recommendations){
		    	//cor: 기존 유사도에 장르 가중치를 포함할 유사도 
		    	double cor = recommendation.getValue();						
		    	String genre = WebtoonListDAO.webtoonDetail(
		    							(int)recommendation.getItemID(), u_no).getGenre_name();
		    	//각 작품의 장르 배열 
		    	String[] genres = genre.split(", ");						
		    	
		    	for(String input_g : input_genres) {
		    		//genres 배열 안에 같은 장르가 있다면 가중치 추가
		    		if(Arrays.asList(genres).contains(input_g)) {			
		    			cor += 0.1;								
		    		}
		    	}
		    	result[recommendations.indexOf(recommendation)][0] = recommendation.getItemID();
		    	result[recommendations.indexOf(recommendation)][1] = cor;
		    }
		    
		    //result 배열 유사도 기준 내림차순 정렬 
		    Arrays.sort(result,(o2,o1) -> {
		    	return Double.compare(o1[1], o2[1]);
		    });

		    for(int i=0; i<result.length; i++) {
		    	//현재 recommendList에 들어간 갯수가 getList_length보다 작고 유사도가 0보다 큰 경우에만 추가. 
		    	if(i < getList_length) {			
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
	//Mysql에서 테이블 가져와서 DataModel로 변환
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
