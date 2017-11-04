package common;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import bullet.Bullet;
import bullet.Bullets;
import enemy.Enemy;
import enemy.Enemys;
import item.Item;
import item.Items;
import player.Player;

public class Operater {
	
	private JPanel panel;
	
	private List<Bullet> bulletList;
	private List<Enemy> enemyList;
	private List<Item> itemList;
	private Player player;
	
	private boolean isGamePause = false;
	private int width, height;
	private Score score;
	
	public Operater(JPanel panel){
		this.panel = panel;
		this.width = panel.getWidth();
		this.height = panel.getHeight();
		
		bulletList = new ArrayList<Bullet>();
		enemyList = new ArrayList<Enemy>();
		itemList = new ArrayList<Item>();
		player = new Player(new Point(0, height - 100));
		
		score = new Score();
		
		panel.addMouseMotionListener(player);
	}
	
	public void paintAll(Graphics2D g){

		Bullets.paintBullets(bulletList, g);
		Enemys.paintEnemys(enemyList, g);
		Items.paintItems(itemList, g);
		player.drawSelf(g);
	}
	
	public void startGame(){

		new MakeEnemy().start();
		new DrawPanel().start();
		new UpdatePanel().start();
		new CheckOutOfScreen().start();
		new CheckIsDamaged().start();
		new PlayerAttack().start();
	}
	
	public void gamePause(){
		isGamePause = true;
	}
	
	public void gameRestart(){
		isGamePause = false;
	}
	
	public Score getScore(){
		return score;
	}
	
	private void checkGameOver(){
		if(player.isDead())
			gamePause();
	}
	
	class MakeEnemy extends Thread{
		@Override
		public void run() {
			while(!isGamePause) {
				
				Enemys.makeEnemy(enemyList, panel.getWidth());
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	class DrawPanel extends Thread{
		@Override
		public void run() {
			while(!isGamePause) {
				
				panel.repaint();

				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	class UpdatePanel extends Thread{
		@Override
		public void run() {
			while(!isGamePause) {
				
				Bullets.moveBullets(bulletList);
				Enemys.moveEnemys(enemyList);
				Items.moveItem(itemList);
				
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	class CheckOutOfScreen extends Thread{
		@Override
		public void run() {
			while(!isGamePause) {
				
				Bullets.checkOutOfScreen(bulletList, 0);
				Enemys.checkOutOfScreen(enemyList, height);
				Items.checkOutOfScreen(itemList, height);
				
				try {
					Thread.sleep(60);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	class CheckIsDamaged extends Thread{
		@Override
		public void run() {
			while(!isGamePause) {
				
				Enemys.checkEnemysDamaged(enemyList, bulletList);
				Enemys.checkEnemyAttackedPlayer(enemyList, player);
				Items.checkItemcrashedPlayer(itemList, player);
				
				Enemys.deletEnemys(enemyList, itemList, score);
				Bullets.deletBullets(bulletList);
				Items.deletItem(itemList, score);
				checkGameOver();
				
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	class PlayerAttack extends Thread{
		@Override
		public void run() {
			while(!isGamePause) {
				Bullets.makeBullet(bulletList, player.getCurrentBulletType());
				
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
