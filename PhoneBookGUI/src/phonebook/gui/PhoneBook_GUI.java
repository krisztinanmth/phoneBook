package phonebook.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Decorations;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/**
 * 
 *To convert Gradle to Maven, first, Maven plugin need to be added in the build.gradle file.
 *Then, simply run Gradle install in the directory. Lastly, simply run gradle install and the
 *directory containing build.gradle will do the job. It will create pom-default.xml in the build/poms subfolder.
 */

public class PhoneBook_GUI {
	
	public PhoneBook_GUI() {
		initUI();
	}

	private void initUI() {		
//		final Display display = Display.getDefault();
		final Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setText("phonebook");
		shell.setLayout(new FillLayout());
		centerWindow(shell);
		
		final Composite wrapperComp = new Composite(shell, SWT.NONE);
		final GridData mainData = new GridData(SWT.FILL, SWT.FILL, true, false);
		wrapperComp.setLayout(new GridLayout(1, true));
		wrapperComp.setLayoutData(mainData);
		
		createHeader(wrapperComp);
		createContent(wrapperComp);
		shell.open();
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}
	
	private void createContent(Composite wrapperComp) {
		
		final Composite contentComp = new Composite(wrapperComp, SWT.NONE);
		final GridData contentData = new GridData(SWT.FILL, SWT.FILL, true, false);
		contentComp.setLayout(new GridLayout(2, false));
		contentComp.setLayoutData(contentData);
		
		Tree contentTree = new Tree(contentComp, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		for (int i = 0; i < 3; i++) {
			TreeItem treeItem = new TreeItem(contentTree, 0);
			treeItem.setText("level: " + i);
		}
	}

	private void createHeader(Composite wrapperComp) {
		
		final Decorations headerComp = new Decorations(wrapperComp, SWT.NONE);
		final GridData headerData = new GridData(SWT.FILL, SWT.FILL, true, false);
		
		// menunel sztem inkabb hasznalj rowLayout-ot!!!!!! .... 
		
		headerComp.setLayout(new GridLayout(1, true));
		headerComp.setLayoutData(headerData);
		
		Menu menu = new Menu(headerComp, SWT.BAR);
		menu.setVisible(true);
		MenuItem menuI = new MenuItem(menu, 1);
		menuI.setText("contacts");
		
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
