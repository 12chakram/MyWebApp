package com.indidge.mywebapp.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.indidge.mywebapp.shared.FieldVerifier;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MyWebApp implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		final FormPanel form = new FormPanel();
	    form.setAction("/authenticationFrom");
	    form.setMethod(FormPanel.METHOD_POST);
	    
	    VerticalPanel panel = new VerticalPanel();
	    form.setWidget(panel);
		
	    final Label userNameErrorLabel = new Label();
		final Label passwordErrorLabel = new Label();
		
		
		final TextBox nameField = new TextBox();
		nameField.getElement().setPropertyString("placeholder", "User Name");
		nameField.setWidth("300px");
	
		panel.add(nameField);
		panel.add(new HTML("</br>"));
		panel.add(userNameErrorLabel);
		 final PasswordTextBox passwordField = new PasswordTextBox();
		passwordField.getElement().setPropertyString("placeholder","Password");
		passwordField.setWidth("300px");
		panel.add(passwordField);
		panel.add(passwordErrorLabel);
		
		panel.add(new HTML("</br>"));
		
		final Button sendButton = new Button("Login" ,new ClickHandler() {
		      public void onClick(ClickEvent event) {
		    	  userNameErrorLabel.setText("");
					if (!FieldVerifier.isValidName( nameField.getText())) {
						passwordErrorLabel.setText("");
						userNameErrorLabel.setText("Please Enter UserName");
						return;
					}
						if (!FieldVerifier.isValidName(passwordField.getText())) {
							userNameErrorLabel.setText("");
							passwordErrorLabel.setText("Please Enter Password");
							return;
						}
					
		        form.submit();
		      }
		    });
		// We can add style names to widgets
		sendButton.addStyleName("width-35 pull-right btn btn-sm btn-primary");
		panel.add(sendButton);
		
		
		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final Label textToServerLabel = new Label();
		final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				sendButton.setEnabled(true);
				sendButton.setFocus(true);
			}
		});
 
		
		

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
	
		RootPanel.get("loginForm").add(panel);
		RootPanel.get("errorLabelContainer").add(userNameErrorLabel);
		RootPanel.get("errorLabelContainer").add(passwordErrorLabel);
		

		// Focus the cursor on the name field when the app loads
		nameField.setFocus(true);
		nameField.selectAll();
		
		
		 form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				
				greetingService.greetServer(nameField.getText(),passwordField.getText(), 
						new AsyncCallback<String>() {
							public void onFailure(Throwable caught) {
								// Show the RPC error message to the user
								dialogBox.setText("Remote Procedure Call - Failure");
								serverResponseLabel
										.addStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(SERVER_ERROR);
								dialogBox.center();
								closeButton.setFocus(true);
							}

							public void onSuccess(String result) {
								dialogBox.setText("Remote Procedure Call");
								serverResponseLabel.removeStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(result);
								dialogBox.center();
								closeButton.setFocus(true);
							}
						});
			}
		    });
		 
		 
		 /**
			 * Send the name from the nameField to the server and wait for a response.
			 */
		 
		// Add a handler to close the DialogBox
		
	}
	
	
}
