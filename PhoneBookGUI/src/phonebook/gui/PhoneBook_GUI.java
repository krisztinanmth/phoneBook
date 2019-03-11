package phonebook.gui;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class PhoneBook_GUI {
	
	public PhoneBook_GUI() {
		initUI();
	}

	private void initUI() {		
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("phonebook");
		shell.setLayout(new GridLayout(1, true));
		centerWindow(shell);
		
		shell.open();
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}
	
	private void centerWindow(Shell shell) {
		Rectangle bds = shell.getDisplay().getBounds();
		Point p = shell.getSize();
		int nLeft = (bds.width - p.x) / 2;
		int nTop = (bds.height - p.y) / 2;
		shell.setBounds(nLeft, nTop, p.x, p.y);
	}

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		PhoneBook_GUI phonebookGUI = new PhoneBook_GUI();
	}

}
