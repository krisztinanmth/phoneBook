import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.krisztinanmth.phonebook.models.Contact;
import com.krisztinanmth.phonebook.services.JSONService;
import com.krisztinanmth.phonebook.services.JSONServiceImpl;

public class Client {
	
	private List<Contact> contacts;
	private static JSONService jsonService = new JSONServiceImpl();
	
	private static final String JSON_PATH = "~/Desktop/phoneBookApp/phoneBook/PhoneBookApp/src/main/resources/contacts.json";
	
	public Client() {
		//jsonService = new JSONServiceImpl();
		contacts = jsonService.readFromJSON(JSON_PATH);
		initUI();
	}

	private void initUI() {
		final Display display = new Display();
		final Shell shell = new Shell(display);
		
		shell.setLayout(new GridLayout(1, true));
		shell.setText("phonebook");
		
		final Composite mainComp = new Composite(shell, SWT.NONE);
		final GridData mainData = new GridData(SWT.FILL, SWT.FILL, true, false);
		mainComp.setLayout(new GridLayout(2, false));
		mainComp.setLayoutData(mainData);
		
//		createHeaderCom(mainComp);
		if (contacts != null) {
			final Tree tree = new Tree(mainComp, SWT.V_SCROLL);
			for (int i = 0; i < contacts.size(); i++) {
				TreeItem item = new TreeItem(tree, SWT.NONE);
				item.setText(String.valueOf(contacts.get(i)));
			}
		}
		
		
		shell.open();
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		
		display.dispose();
		
	}

//	private void createHeaderCom(Composite mainComp) {
//		// TODO Auto-generated method stub
//		
//	}

	public static void main(String[] args) {
		
		@SuppressWarnings("unused")
		Client client = new Client();
	}

}
