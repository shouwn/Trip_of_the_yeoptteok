package enemy;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import common.Point;
import common.Points;
import item.Item;
import item.Items;
import player.Player;

public class TypeE05 implements Enemy{

	private int score;
	private int life;
	private Point point;
	private float speed;
	private int itemProbability;
	private static Map<Item, Integer> eachItemProbability = new HashMap<Item, Integer>();
	private boolean isOutOfScreen = false;
	private static BufferedImage[] images = new BufferedImage[3];
	private BufferedImage currentImage;

	static {
		try {
			images[0] = ImageIO.read(new File("image/Enemy/TypeE05.gif"));

		} catch (IOException e) {
			System.err.println("Fail Load TypeE05 Image");
			System.exit(0);
		}

		eachItemProbability.put(Items.getItemType(Items.ITEM_FORK), 25);
		eachItemProbability.put(Items.getItemType(Items.COOL_PEACE_LARGE), 25);
		eachItemProbability.put(Items.getItemType(Items.COOL_PEACE), 25);
		eachItemProbability.put(Items.getItemType(Items.RICE_BALL), 25);
	}

	public TypeE05() {

	}

	public TypeE05(Point point) {
		currentImage = images[0];
		score = 150;
		speed = 25;
		life = 5;
		itemProbability = 100;

		this.point = point;
	}

	@Override
	public Enemy makeSelf(Point point) {
		return new TypeE05(point);
	}

	@Override
	public void moveSelf() {
		point.move(0, -speed);
	}

	@Override
	public void drawSelf(Graphics2D g) {
		g.drawImage(currentImage, AffineTransform.getTranslateInstance(point.getX(), point.getY()), null);
	}


	@Override
	public boolean isCrashed(Player player) {

		if(checkDead())
			return false;

		if(Points.checkAreaInArea(
				player.getPoint(),
				new Point(player.getPoint()).add(player.getWidth(), player.getHeight()),
				this.point,
				new Point(this.point).add(getWidth(), getHeight())))

			return true;

		return false;
	}

	@Override
	public void fallLife() {
		if(--life >= 1)
			currentImage = images[0];
	}

	@Override
	public boolean checkDead() {
		return life <= 0;
	}

	@Override
	public void setDead() {
		life = 0;
	}

	@Override
	public Point getPoint(){
		return point;
	}

	@Override
	public int getWidth() {
		return images[0].getWidth();
	}

	@Override
	public int getHeight() {
		return images[0].getHeight();
	}

	@Override
	public int getScore() {
		return score;
	}

	@Override
	public int getItemProbability() {
		return itemProbability;
	}

	@Override
	public boolean checkOutOfScreen() {
		return isOutOfScreen;
	}

	@Override
	public void setOutOfScreen() {
		isOutOfScreen = true;
	}

	@Override
	public Map<Item, Integer> getEachItemProbability() {
		return eachItemProbability;
	}
}
