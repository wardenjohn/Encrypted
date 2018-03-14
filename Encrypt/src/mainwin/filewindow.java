package mainwin;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class filewindow extends JFrame{
	public StringBuffer choose_file() {
		//JFileChooser chooser = new JFileChooser("/Users/zhangyongde/Desktop");
		JFileChooser chooser = new JFileChooser();
		int i=chooser.showOpenDialog(getContentPane());
		StringBuffer filename = new StringBuffer(chooser.getSelectedFile().getName());
		StringBuffer filepath = new StringBuffer(chooser.getSelectedFile().getAbsolutePath());
		
		return filepath;
	}
}
