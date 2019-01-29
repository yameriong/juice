package com.test.juice;

import com.test.juice.domain.Fruit;
import lombok.*;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
public class JuiceApplicationTests {

	// 주문, 레시피
	public class Order {

	}

	interface Bowl {
		boolean putFuit(Fruit fruit);
	}

	// 아쉬운점 유동적으로 바뀌는 maxSize
	@Getter
	@Setter
	public class SmallBowl implements Bowl {
		private int maxSize = 2;
		private List<Fruit> bowl = new ArrayList<>(0);

		@Override
		public boolean putFuit(Fruit fruit) {
			if (bowl.size()<maxSize) {
					bowl.add(fruit);
					return true;
			}else {
				System.out.println("사이즈 오버!!");
				return false;
			}
		}
	}


	abstract class Blender {
		void doBlend(List<Fruit> bowl){

		}
		void timer(int time){
			try {
				//assuming it takes 20 secs to complete the task
				Thread.sleep(time*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		void alarm(int time){
			System.out.println("음료를 만들어요! " + "대기시간은 " +time+"초 예상입니다.");
			timer(time);
			System.out.println("(진동밸) 부르르르르르르르르르르");
		}
		String setName(List<Fruit> bowl){
			StringBuilder stringBuilder = new StringBuilder();
			if(bowl.size()>1) {
				for (Fruit fruit : bowl) {
					stringBuilder.append(fruit.getName().toString().substring(0, 1));
				}
			}else {
				//bowl.
			}
			return String.valueOf(stringBuilder);
		}
	}

	public class StoneBlender extends Blender{

		@Override
		public void doBlend(List<Fruit> bowl) {
			System.out.println("맷돌 사용중");
			System.out.println("맷돌 "+setName(bowl)+" 나왔습니다.");
		}
	}

	public class NinjaBlender  extends Blender{

		@Override
		public void doBlend(List<Fruit> bowl) {
			System.out.println("닌자 사용중");
			System.out.println(setName(bowl)+"슬러쉬가 나왔습니다.");
		}
	}

	@Data
	public class Refrigerator {
		private List<Fruit> refrigerator;
		// 냉장고에 과일 세팅
		// 냉장고에서 물건 꺼내줌

		public Fruit getFruitOnce(String fruitName){
			return this.refrigerator.stream()
					.filter(fruit -> fruitName.equals(fruit.getName()))
					.findAny()
					.orElse(null);
		}

		public void fruitAbrogate(String fruitName){
			this.refrigerator.remove(getFruitOnce(fruitName));
		}

	}


	@Test
	public void testMain() {
		final Refrigerator refrigerator = new Refrigerator();
		SmallBowl smallBowl = new SmallBowl();
		StoneBlender stoneBlender = new StoneBlender();
		NinjaBlender ninjaBlender = new NinjaBlender();

		refrigerator.setRefrigerator(Lists.newArrayList(
				Fruit.builder().gram(2000).name("사과").expirationDate(LocalDateTime.now().minusDays(2)).build(),
				Fruit.builder().gram(2000).name("바나나").expirationDate(LocalDateTime.now().plusDays(2)).build(),
				Fruit.builder().gram(2000).name("딸기").expirationDate(LocalDateTime.now().plusDays(2)).build())
		);


		Fruit strawberry = refrigerator.getFruitOnce("딸기");
		Fruit banana = refrigerator.getFruitOnce("바나나");

		if(strawberry.isCondition()){
			refrigerator.fruitAbrogate("딸기");
		}

		strawberry.grepFruit();



		smallBowl.putFuit(strawberry);
		smallBowl.putFuit(banana);




		stoneBlender.doBlend(smallBowl.getBowl());
		stoneBlender.alarm(5);

		ninjaBlender.doBlend(smallBowl.getBowl());
		ninjaBlender.alarm(5);

		//담을때 유통기한 지났으면 냉장고에서 패기하는 기능 필요

		// 보울에 담는다. 레시피 만큼 담는다 -> 냉장고 과일에 양이 줄어든다

		// 보울은 사이즈가 있다
		// 보울은 블렌더몸체랑 하나가 됨니당.
		// 레시피에 따라 블렌더 몸체 변경?
		System.out.println("refrigerator = " + refrigerator.getRefrigerator());
	}

}