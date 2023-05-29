import enigma.core.Enigma;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import enigma.console.TextAttributes;
import java.awt.Color;

public class Game {
	public static enigma.console.Console cn = Enigma.getConsole("Chain", 80, 40, 20, 15);
	public static TextAttributes Red = new TextAttributes(Color.RED);
	public KeyListener klis;
	public Chain chain = new Chain();
	public static DoubleLinkedList hstable = new DoubleLinkedList();

	// ------ Standard variables for mouse and keyboard ------
	public int keypr; // key pressed?
	public int rkey; // key (for press/release)
	// ----------------------------------------------------

	public static char[][] map = Map.map;

	static boolean gameover = false;
	static public int score = 0, x, y;
	public static int gamenumber = 0;
	public static int round = 0;
	public static boolean restart=false;

	public static MultiLinkedList table = new MultiLinkedList();

	Game() throws Exception {
		klis = new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
				if (keypr == 0) {
					keypr = 1;
					rkey = e.getKeyCode();
				}
			}

			public void keyReleased(KeyEvent e) {
			}
		};
		cn.getTextWindow().addKeyListener(klis);
		// ----------------------------------------------------

		/*
		 * cn.getTextWindow().output("Do you want to enter a seed?"); if(keypr == 1){
		 * if(rkey==KeyEvent.VK_Y){ while(rkey!=KeyEvent.VK_ENTER) { Map.seed; } }
		 * keypr=0; }
		 */

		cn.getTextWindow().setCursorPosition(35, 0);
		cn.getTextWindow().output("Board Seed: " + Map.seed);
		cn.getTextWindow().setCursorPosition(35, 1);
		cn.getTextWindow().output("Round: " + round);
		cn.getTextWindow().setCursorPosition(35, 2);
		cn.getTextWindow().output("Score: " + score);
		cn.getTextWindow().setCursorPosition(35, 3);
		cn.getTextWindow().output("---------------------------------------");
		cn.getTextWindow().setCursorPosition(35, 4);
		cn.getTextWindow().output("Table: ");
		if (gamenumber == 0) {
	        try (BufferedReader br = new BufferedReader(new FileReader("highscoretable.txt"))) {
	            String line;
	            while ((line = br.readLine()) != null) {
	                String[] elements = line.split(" ");
	                if (elements.length >= 3) {
	                    String name = elements[0] + " " + elements[1];
	                    int score = Integer.parseInt(elements[elements.length - 1]);
	                    hstable.add_back(name);
	                    hstable.add_back(score);
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        gamenumber ++;
		}

		x = 1;
		y = 0;
		map[y][x] = '|';
		cn.getTextWindow().output(x, y, '|');

		while (!gameover) {
			if (keypr == 1) { // if keyboard button pressed
				if (map[y][x] == ' ' || map[y][x] == '|') {
					map[y][x] = ' ';
					cn.getTextWindow().output(x, y, ' ');
				} else if (map[y][x] == '+') {
					map[y][x] = '+';
					cn.getTextWindow().output(x, y, '+', Red);
				}
				if (rkey == KeyEvent.VK_LEFT && x > 0) {
					if (y % 2 != 0 && x - 1 == 0) {
						x--;
					} else if (x - 2 >= 0) {
						x -= 2;
					}
				}
				if (rkey == KeyEvent.VK_RIGHT) {
					if (y % 2 != 0 && x < 30 && x + 1 == 30) {
						x++;
					} else if (x + 2 <= 30)
						x += 2;
				}
				if (rkey == KeyEvent.VK_UP && y > 0) {
					if (x % 2 == 0 && map[y - 1][x] != ' ') {
						if (x - 1 > -1) {
							x--;
						} else
							x++;
					} else if (y - 1 % 2 != 0 && x % 2 != 0) {
						if (x - 1 > -1) {
							x++;
						} else
							x--;
					}
					y--;
				}
				if (rkey == KeyEvent.VK_DOWN && y < 18) {
					if (x % 2 == 0 && map[y + 1][x] != ' ') {
						if (x - 1 > -1) {
							x--;
						} else
							x++;
					} else if (y + 1 % 2 != 0 && x % 2 != 0) {
						if (x - 1 > -1) {
							x++;
						} else
							x--;
					}
					y++;
				}
				// Add +
				if (rkey == KeyEvent.VK_SPACE) {
					if (map[y][x] == '+') {
						cn.getTextWindow().output(x, y, ' ');
						map[y][x] = 0;
						int[] position = { x, y };
						chain.removePlus(position);
					} else {
						map[y][x] = '+';
						cn.getTextWindow().output(x, y, '+', Red);
						int[] position = { x, y };
						chain.getChain().add(position);
					}
				}
				// Enter
				if (rkey == KeyEvent.VK_ENTER) {
					round++;
					cn.getTextWindow().setCursorPosition(35, 1);
					cn.getTextWindow().output("Round: " + round);
					chain.newRound();
					table.display();
				}
				// Quit
				if (rkey == KeyEvent.VK_E) {
					gameover = true;
				}

				char rckey = (char) rkey;
				// left right up down
				if (rckey == '%' || rckey == '\'' || rckey == '&' || rckey == '(') {
					cn.getTextWindow().output(x, y, '|');
				}

				// Output for +
				if (map[y][x] == '+') {
					cn.getTextWindow().output(x, y, '|');
				} else {
					map[y][x] = '|';
					cn.getTextWindow().output(x, y, '|');
				}
				keypr = 0; // last action
			}
			Thread.sleep(200);
		}
		if (gameover) {
			reset();
			cn.getTextWindow().setCursorPosition(0, 0);
			cn.getTextWindow().output("Game over");			
			hstable.add_back(Menu.username);
			hstable.add_back(score);
			hstable= sortHighScoreTable(hstable);
			cn.getTextWindow().setCursorPosition(0, 3);
			hstable.displaytable();		
			score=0;
			Thread.sleep(2000);
			reset();
		}
	}

	public static DoubleLinkedList sortHighScoreTable(DoubleLinkedList hstable) {
		DoubleLinkedList hscore = new DoubleLinkedList();
		int max = 0;
		DLLNode currentnode = hstable.head.getNext();
		DLLNode maxnode = hstable.head.getNext();
		hstable.add_back(" ");
		hstable.add_back(" ");
		int hstablesize = hstable.size()-2;
		while (hstablesize != hscore.size()) {
			currentnode = hstable.head.getNext();
			while (currentnode.getNext() != null) {
				if (Integer.parseInt(currentnode.getData().toString()) > max) {
					max = Integer.parseInt(currentnode.getData().toString());
					maxnode = currentnode;
				}
				currentnode = currentnode.getNext().getNext();
			}
			hscore.add_back(maxnode.getPrev().getData());
			hscore.add_back(maxnode.getData());
			maxnode.setData(Integer.MIN_VALUE);
			hstable.delete(maxnode.getPrev().getData());
			hstable.delete(maxnode.getData());
			max = Integer.MIN_VALUE;
		}
		return hscore;
	}
        
	public static void reset() {
		Menu.clear_screen();
		Map.delete();
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				map[i][j]=' ';
			}
		}
	}	
}
