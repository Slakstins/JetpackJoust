import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashMap;

import javax.swing.JComponent;
import javax.swing.JLabel;

public class ScreenComponent extends JComponent {
	private int levelNum;
	private HashMap<String, Boolean> keyMap;
	private Level level;
	private int lives = 3;

	private boolean levelChange;

	public ScreenComponent() {
		this.levelNum = 1;
		this.levelChange = true;
	}

	public void addLevel() {
		this.levelNum += 1;
		this.levelChange = true;
	}

	public void removeLevel() {
		this.levelNum -= 1;
		this.levelChange = true;
	}

	public int getLevel() {
		return this.levelNum;
	}

	public void setKeyMap(HashMap<String, Boolean> keyMap) {
		this.keyMap = keyMap;

		if (this.keyMap.get("left") == false) {
		}
	}
	
	public void goUpALevelIfMonstersDead() {
		if (this.level.getMobsToDraw().size() < 2 && !this.level.checkHeroDead()) {
			this.addLevel();
		}
	}
	
	public void checkHeroDeath() {
		if (this.level.checkHeroDead()) {
			this.levelChange = true;
			this.lives -= 1;
		}
		if (this.lives < 0) {
			this.gameOver();
		}
	}
	public void gameOver() {
		this.levelChange = true;
		this.levelNum = -1; // freezes game because lives is still < 0
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		JLabel lives = new JLabel();

		if (this.levelChange) {
			this.level = new Level("Level " + this.levelNum, this.levelNum);
			this.level.setKeyMap(keyMap);
			this.level.readLevelFile();
			this.levelChange = false;
		}
		this.level.drawEverything(g2);
		this.goUpALevelIfMonstersDead();
		this.checkHeroDeath();
	}

	public void updateDraw() {

		this.repaint();
	}

}
