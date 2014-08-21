package main;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class ClassListing extends JPanel
{
	private JTextField numField;
	private JButton xButton;
	
	public ClassListing()
	{
		init();
	}

	public ClassListing(String newClassCode)
	{
		init();
		numField.setText(newClassCode);
	}
	
	private void init()
	{
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		numField = new JTextField();
		numField.setEditable(false);
		xButton = new JButton("X");
		xButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				((ClassListing)(((JComponent)(e.getSource())).getParent())).remove();
			}
		});
		add(xButton);
		add(numField);
		setMaximumSize(new Dimension((int)(getMaximumSize().getWidth()),
				(int)(getMinimumSize().getHeight())));
	}
	
	public void remove()
	{
		Container parent = getParent();
		parent.remove(this);
		parent.revalidate();
		parent.repaint();
		parent.requestFocusInWindow();
	}
	
	public Integer getCRN()
	{
		return Integer.parseInt(numField.getText());
	}
}
