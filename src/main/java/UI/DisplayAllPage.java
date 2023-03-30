package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import DB.DBUtil;
import businessLogic.Util;
import persistence.LedgerItem;
import persistence.User;

public class DisplayAllPage implements ActionListener {

	public JFrame displayFrame;
	private JPanel mainDisplayPanel;
	private JPanel mainPanel; // main panel to hold all other panels in the expense page form
	private JButton toMenu;
	private JLabel title;
	private JButton export;
	public JTable expenseTable;
	private JScrollPane displayScroller;
	private NavigatorPage navigation;

	public DisplayAllPage() {

		try {
			expenseTable = DBUtil.query(User.getLoginAs(), "tag", "all");
		} catch (SQLException er) {
		}
		displayScroller = new JScrollPane(expenseTable);

		displayFrame = new JFrame();
		displayFrame.setLocationRelativeTo(null);
		mainDisplayPanel = new JPanel();

		// Initialize main title on page, along with initializing button and layouts
		title = new JLabel("All Items");
		title.setSize(30, 30);
		title.setFont(new Font("Tahoma", Font.BOLD, 60));

		toMenu = new JButton(new AbstractAction("Main Menu") {

			@Override
			public void actionPerformed(ActionEvent e) {
				navigation = new NavigatorPage();
				displayFrame.dispose();
			}
		});

		// export file
		export = new JButton(new AbstractAction("Export current page as Excel file") {

			@Override
			public void actionPerformed(ActionEvent e) {
				JTextField filenameField = new JTextField();
				filenameField.setColumns(20);
				filenameField.setPreferredSize(new Dimension(200, filenameField.getPreferredSize().height));
				JButton submitButton = new JButton("Submit");
				JPanel panel = new JPanel();
				panel.add(new JLabel("Enter filename: "));
				panel.add(filenameField);
				panel.add(submitButton);
				JDialog dialog = new JDialog();
				dialog.setPreferredSize(new Dimension(300, 200));
				dialog.add(panel);
				dialog.pack();
				dialog.setLocationRelativeTo(null);
				dialog.setVisible(true);

				submitButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						String filename = filenameField.getText();
						dialog.dispose();
						File outputFile = new File("./Excel Sheets Exported/" + filename + ".xlsx");
						Util.exportToExcel(expenseTable, outputFile);
						JOptionPane.showMessageDialog(null, "Export successfully to \"Excel Sheets Exported\" folder.");
					}
				});
			}
		});
		export.setForeground(Color.green);
		export.setPreferredSize(new Dimension(150, 50));
		// This panel holds the top elements including the title and the ability to add
		// another button
		mainDisplayPanel.setLayout(new GridLayout(1, 3));
		mainDisplayPanel.add(title);
		mainDisplayPanel.add(toMenu);
		mainDisplayPanel.setBackground(Color.green);

		// This panel holds all other elements in the frame
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		mainPanel.add(mainDisplayPanel);
		mainPanel.setBackground(Color.green);

		// This is the main frame which holds the main panel and all other elements
		// enclosed in it
		displayFrame.add(mainPanel, BorderLayout.NORTH);
		// mainEpFrame.add(ledgerInfo, BorderLayout.CENTER);
		displayFrame.add(displayScroller, BorderLayout.CENTER);
		displayFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		displayFrame.add(export, BorderLayout.SOUTH);
		displayFrame.setTitle("Display all");
		displayFrame.setSize(1000, 1000);
		// expensePageFrame.pack(); // when setSize on, then remove pack
		displayFrame.setVisible(true);

	}

	public static void main(String[] args) {
		new DisplayAllPage();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}
