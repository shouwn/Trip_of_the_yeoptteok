package enemy;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;

import common.Point;
import common.Sound;
import item.Item;
import player.Player;


public class Boss implements Enemy {

	private int score;
	private float speed;
	private Point point;
	private static BufferedImage image ;
	private int width, height;
	private int life;

	private int area;

	static{
		try {
			image = ImageIO.read(new File("image/Enemy/boss.gif"));
		} catch (IOException e) {
			System.err.println("Fail Load Boss Image");
			System.exit(0);
		}
	}

	public Boss() {

	}

	public Boss(Point point) {
		score = 1000;
		speed = 10;
		life = 10;

		this.width=image.getWidth();
		this.height=image.getHeight();
		this.point = point;
	}


	@Override
	public boolean checkDead(){
		boolean result = life <= 0;
		if(result)
			new Sound(Sound.BOSSDEAD).playMusic(false);
		return life <= 0;
	}

	@Override
	public void fallLife() {
		new Sound(Sound.HITBOSS).playMusic(false);
		life--;
	}

	@Override
	public Boss makeSelf(Point point) {
		new Sound(Sound.BOSS).playMusic(false);

		return new Boss(point);
	}

	@Override
	public void moveSelf() {
		float pre = point.getX() + speed;

		if(pre < 0 || pre > area - width){
			speed *= -1;
			pre = point.getX() + speed;
		}

		point.setX(pre);;
	}
	@Override
	public void drawSelf(Graphics2D g){
		g.drawImage(image, AffineTransform.getTranslateInstance(point.getX(), point.getY()), null);
	}


	@Override
	public int getHeight(){
		return image.getHeight();
	}
	@Override
	public int getWidth(){
		return image.getWidth();
	}
	@Override
	public Point getPoint(){
		return point;
	}
	@Override
	public int getScore() {
		return score;
	}


	@Override
	public boolean isCrashed(Player player) {

		return false;
	}


	@Override
	public boolean checkOutOfScreen() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getItemProbability() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Map<Item, Integer> getEachItemProbability() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOutOfScreen() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDead() {
		// TODO Auto-generated method stub

	}

	public void setArea(int area){
		this.area = area;
	}
}
