package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class principal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldFirstPlayer;
	private JTextField textFieldSecondPlayer;
	private String firstPlayerName;
	private String secondPlayerName;
	private boolean canDrawMenu = true;
	private boolean canDrawSquares = false;
	private int role = 0; // 0 = X, 1 = O
	private int turn = 0;
	private JPanel[][] squares = new JPanel[3][3];
	private JLabel statusLabel;
	private JButton restartButton;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				principal frame = new principal();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public principal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 784, 581);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		drawTitle();
		drawMenu();
	}

	private void drawTitle() {
		JLabel lblNewLabel = new JLabel("Tic Tac Toe");
		lblNewLabel.setBounds(257, 11, 253, 42);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel);
	}

	private void drawMenu() {
		if (canDrawMenu) {
			JLabel lblFirstPlayerName = new JLabel("First Player Name:");
			lblFirstPlayerName.setHorizontalAlignment(SwingConstants.CENTER);
			lblFirstPlayerName.setBounds(10, 145, 144, 42);
			contentPane.add(lblFirstPlayerName);

			JLabel lblSecondPlayerName = new JLabel("Second Player Name:");
			lblSecondPlayerName.setHorizontalAlignment(SwingConstants.CENTER);
			lblSecondPlayerName.setBounds(10, 213, 144, 42);
			contentPane.add(lblSecondPlayerName);

			textFieldFirstPlayer = new JTextField();
			textFieldFirstPlayer.setBounds(155, 158, 140, 20);
			contentPane.add(textFieldFirstPlayer);
			textFieldFirstPlayer.setColumns(10);

			textFieldSecondPlayer = new JTextField();
			textFieldSecondPlayer.setColumns(10);
			textFieldSecondPlayer.setBounds(155, 224, 140, 20);
			contentPane.add(textFieldSecondPlayer);

			JRadioButton rdbtnX = new JRadioButton("X");
			rdbtnX.setBounds(154, 292, 48, 23);
			contentPane.add(rdbtnX);

			JRadioButton rdbtnO = new JRadioButton("O");
			rdbtnO.setBounds(154, 318, 48, 23);
			contentPane.add(rdbtnO);

			ButtonGroup group = new ButtonGroup();
			group.add(rdbtnX);
			group.add(rdbtnO);

			JButton btnPlay = new JButton("Play");
			btnPlay.addActionListener(e -> startGame());
			btnPlay.setBounds(155, 416, 140, 23);
			contentPane.add(btnPlay);
		}
	}

	private void startGame() {
		if (contentPane.getComponentCount() > 0) {
			removeMenuComponents();
		}

		firstPlayerName = textFieldFirstPlayer.getText();
		secondPlayerName = textFieldSecondPlayer.getText();
		role = getSelectedRole();

		canDrawSquares = true;
		drawGameBoard();
	}

	private int getSelectedRole() {
		for (int i = 0; i < contentPane.getComponentCount(); i++) {
			if (contentPane.getComponent(i) instanceof JRadioButton) {
				JRadioButton button = (JRadioButton) contentPane.getComponent(i);
				if (button.isSelected()) {
					return button.getText().equals("X") ? 0 : 1;
				}
			}
		}
		return 0;
	}

	private void removeMenuComponents() {
		contentPane.removeAll();
		contentPane.revalidate();
		contentPane.repaint();
	}

	private void drawGameBoard() {
		if (canDrawSquares) {
			statusLabel = new JLabel("");
			statusLabel.setBounds(300, 450, 200, 50);
			contentPane.add(statusLabel);

			for (int i = 1; i < 3; i++) {
				JPanel horizontalLine = new JPanel();
				horizontalLine.setBackground(Color.BLACK);
				horizontalLine.setBounds(100, 100 + i * 100, 300, 4);
				contentPane.add(horizontalLine);
			}

			for (int i = 1; i < 3; i++) {
				JPanel verticalLine = new JPanel();
				verticalLine.setBackground(Color.BLACK);
				verticalLine.setBounds(100 + i * 100, 100, 4, 300);
				contentPane.add(verticalLine);
			}

			for (int row = 0; row < 3; row++) {
				for (int col = 0; col < 3; col++) {
					final int r = row;
					final int c = col;

					squares[row][col] = new JPanel();
					squares[row][col].setBounds(100 + col * 100, 100 + row * 100, 100, 100);
					squares[row][col].setBackground(Color.WHITE);
					squares[row][col].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

					squares[row][col].addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							if (squares[r][c].getComponentCount() < 1 && !checkWin()) {
								playTurn(r, c);
								if (checkWin()) {
									displayWinner();
								} else if (isBoardFull()) {
									displayDraw();
								}
							}
						}
					});

					contentPane.add(squares[row][col]);
				}
			}

			contentPane.revalidate();
			contentPane.repaint();
		}
	}

	private void playTurn(int row, int col) {
		if (turn == 0) {
			JLabel labelX = new JLabel("X", SwingConstants.CENTER);
			labelX.setFont(labelX.getFont().deriveFont(48f));
			squares[row][col].add(labelX);
			turn = 1;
		} else {
			JLabel labelO = new JLabel("O", SwingConstants.CENTER);
			labelO.setFont(labelO.getFont().deriveFont(48f));
			squares[row][col].add(labelO);
			turn = 0;
		}
		squares[row][col].revalidate();
		squares[row][col].repaint();
	}

	private boolean checkWin() {
		for (int i = 0; i < 3; i++) {
			if (checkLine(squares[i][0], squares[i][1], squares[i][2])
					|| checkLine(squares[0][i], squares[1][i], squares[2][i])) {
				return true;
			}
		}
		return checkLine(squares[0][0], squares[1][1], squares[2][2])
				|| checkLine(squares[0][2], squares[1][1], squares[2][0]);
	}

	private boolean checkLine(JPanel a, JPanel b, JPanel c) {
		if (a.getComponentCount() > 0 && b.getComponentCount() > 0 && c.getComponentCount() > 0) {
			String valueA = ((JLabel) a.getComponent(0)).getText();
			String valueB = ((JLabel) b.getComponent(0)).getText();
			String valueC = ((JLabel) c.getComponent(0)).getText();
			return valueA.equals(valueB) && valueB.equals(valueC);
		}
		return false;
	}

	private void displayDraw() {
		statusLabel.setText("It's a draw!");
		showRestartButton();
	}

	private void displayWinner() {
		statusLabel.setText("Player " + (turn == 0 ? "O" : "X") + " wins!");
		showRestartButton();
	}

	private void showRestartButton() {
		if (restartButton == null) {
			restartButton = new JButton("Restart");
			restartButton.setBounds(300, 510, 150, 30);
			restartButton.addActionListener(e -> restartGame());
			contentPane.add(restartButton);
		}
		contentPane.revalidate();
		contentPane.repaint();
	}

	private boolean isBoardFull() {
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				if (squares[row][col].getComponentCount() < 1) {
					return false;
				}
			}
		}
		return true;
	}

	private void restartGame() {
		removeGameBoard();
		resetGame();
		drawMenu();
		contentPane.revalidate();
		contentPane.repaint();
	}

	private void removeGameBoard() {
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				contentPane.remove(squares[row][col]);
			}
		}
		contentPane.remove(statusLabel);
		contentPane.remove(restartButton);
	}

	private void resetGame() {
		canDrawMenu = true;
		canDrawSquares = false;
		role = 0;
		turn = 0;
		squares = new JPanel[3][3];
	}
}
