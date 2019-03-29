package com.contacts.ui;

import java.util.function.Consumer;

import org.eclipse.jface.wizard.Wizard;

import com.krisztinanmth.phonebook.models.Contact;

public class CreateContactWizard extends Wizard {
	
	protected final Consumer<Contact> onWizardFinish;
	
	private AddNewContactPage addNewContactPage;
	
	public CreateContactWizard(Consumer<Contact> consumer) {
		super();
		this.onWizardFinish = consumer;
	}
	
	@Override
	public String getWindowTitle() {
		return "🧙🏻‍♂️ I AM THE MIGHTIEST WIZARD :) 🧙";
	}
	
	@Override
	public void addPages() {
		addNewContactPage = new AddNewContactPage("For creating a new contact, please provide all required fields and press the finish button.");
		addPage(addNewContactPage);
	}
	
	@Override
	public boolean performFinish() {
		if (addNewContactPage.isPageComplete()) {
			onWizardFinish.accept(addNewContactPage.getContact());
			return true;
		}
		return false;
	}
}
