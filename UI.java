package main;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class UI
{
	public static void main(String[] args)
	{
		JFrame mainFrame;
        mainFrame = new JFrame("RPI Class Router");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(new Dimension(600, 300));
        mainFrame.setMinimumSize(new Dimension(200, 300));
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setExtendedState(mainFrame.getExtendedState()|JFrame.MAXIMIZED_BOTH);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.LINE_AXIS));

        final JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.PAGE_AXIS));
        controlPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel mapPanel = new JPanel();
        mapPanel.setLayout(new BoxLayout(mapPanel, BoxLayout.PAGE_AXIS));
        mapPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel addPanel = new JPanel();
        addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.LINE_AXIS));
        addPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        final JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.PAGE_AXIS));
        listPanel.setAlignmentX(Component.CENTER_ALIGNMENT);        
        
        final Map[] maps = new Map[1];
        
        final JTextField addField = new JTextField();
        
        JButton addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				String testNum = addField.getText();
				
				boolean valid = true;
				try {
					if (! Course.isValidCRN(Integer.parseInt(testNum)))
					{
						valid = false;
					}
				} catch (NumberFormatException ex) {
					valid = false;
				}
				
				if (valid)
				{
					listPanel.add(new ClassListing(testNum));
					addField.setText("");

					listPanel.revalidate();
					listPanel.repaint();
					
					addField.requestFocusInWindow();
				}
				else
				{
					JOptionPane.showMessageDialog(null,
							"\"" + testNum + "\" is not a valid RPI CRN.  Please make"
								+ "\nsure that you have entered all digits correctly.",
								"Invalid CRN", JOptionPane.ERROR_MESSAGE);
					
					addField.requestFocusInWindow();
				}
			}
		});
		
		addPanel.add(addButton);
		addPanel.add(addField);
		addPanel.setMaximumSize(new Dimension(
				(int)(addPanel.getMaximumSize().getWidth()),
				(int)(addPanel.getMinimumSize().getHeight())));
		
		String [] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        final JComboBox<String> daysComboBox = new JComboBox<String>(days);
        daysComboBox.setMaximumSize(new Dimension(100,
        		(int)(daysComboBox.getMinimumSize().getHeight())));
        daysComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton calculateButton = new JButton("Calculate My Route");
        calculateButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				ArrayList<Integer> cRNs = new ArrayList<Integer>();
				for (Component comp: listPanel.getComponents())
				{
					cRNs.add(((ClassListing)comp).getCRN());
				}
				int day = 0;
				if ("Monday".equals(daysComboBox.getSelectedItem()))
				{
					day = 1;
				}
				else if ("Tuesday".equals(daysComboBox.getSelectedItem()))
				{
					day = 2;
				}
				else if ("Wednesday".equals(daysComboBox.getSelectedItem()))
				{
					day = 3;
				}
				else if ("Thursday".equals(daysComboBox.getSelectedItem()))
				{
					day = 4;
				}
				else if ("Friday".equals(daysComboBox.getSelectedItem()))
				{
					day = 5;
				}
				maps[0] = new Map(new Schedule(cRNs).getPaths(day));
				mapPanel.removeAll();mapPanel.revalidate();
				mapPanel.add(maps[0]);
				mapPanel.revalidate();
				mapPanel.repaint();
			}
		});
        
        maps[0] = new Map();
        maps[0].setAlignmentX(Component.CENTER_ALIGNMENT);

        controlPanel.add(addPanel);
        controlPanel.add(new JScrollPane(listPanel));
        controlPanel.add(daysComboBox);
        controlPanel.add(calculateButton);
        
        controlPanel.setMaximumSize(new Dimension(200,
        		(int)(controlPanel.getMaximumSize().getHeight())));
        controlPanel.setMinimumSize(controlPanel.getMaximumSize());
        controlPanel.setPreferredSize(controlPanel.getMaximumSize());
        controlPanel.setSize(controlPanel.getMaximumSize());
        
        mapPanel.add(maps[0]);
        
        mainPanel.add(controlPanel);
        mainPanel.add(mapPanel);
        
        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
        
        addField.requestFocusInWindow();
	}
}
