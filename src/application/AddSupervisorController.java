package application;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class AddSupervisorController 
{
	@FXML
	private RadioButton male;
	@FXML
	private RadioButton female;
	@FXML
	private TextField id;
	@FXML
	private TextField fname;
	@FXML
	private TextField lname;
	@FXML
	private TextField mno;
	@FXML
	private TextArea add;
	@FXML
	private Label status;
	

	// Event Listener on Button.onAction
	@FXML
	public void addEmp(ActionEvent event) 
	{
		// TODO Autogenerated
		int t1=0;
		String ffname="",llname="",mmno="",aadd="";
		Boolean m=false,flag=false;
		try
		{
			t1 =Integer.parseInt(id.getText());
			ffname=fname.getText();
			llname=lname.getText();
			mmno=mno.getText();
			aadd=add.getText();
			m=male.isSelected();
			flag=check(t1,ffname,llname,mmno,aadd);
			
		}
		catch(Exception e)
		{
			//status.setText("Please Enter Correct Credentials");
			Stage dialogStage = new Stage();
		    dialogStage.initModality(Modality.WINDOW_MODAL);

		    VBox vbox = new VBox(new Text("Enter valid credentials!!!"));
		    vbox.setAlignment(Pos.CENTER);
		    vbox.setPadding(new Insets(15));
		    vbox.setPrefSize(400, 100);

		    dialogStage.setScene(new Scene(vbox));
		    dialogStage.show();
		}
		
		if(flag!=false)
		{
			try 
			{ 
				String url = "jdbc:mysql://localhost:3306/factory"; 
				Connection conn = (Connection) DriverManager.getConnection(url,"root","root"); 
				Statement stmt = (Statement) conn.createStatement();
				String query = "INSERT INTO superv ( id, fname, lname, gender, mno, address)" + " values (?, ?, ?, ?, ?, ?)";
				PreparedStatement ps = (PreparedStatement) conn.prepareStatement(query);
				ps.setInt (1, t1);
				ps.setString (2, ffname);
				ps.setString (3, llname);
				if(m==true)
					ps.setString (4,"Male" );
				else
					ps.setString (4,"Female" );
				ps.setString (5,mmno );
				ps.setString (6, aadd);
				ps.execute();
				
				String sql = "alter table superv order by id";
	            stmt.executeUpdate(sql);
				//status.setText("Added SuccessFully");
				
				Stage dialogStage = new Stage();
			    dialogStage.initModality(Modality.WINDOW_MODAL);

			    VBox vbox = new VBox(new Text("Added Successfully!!!"));
			    vbox.setAlignment(Pos.CENTER);
			    vbox.setPadding(new Insets(15));
			    vbox.setPrefSize(400, 100);

			    dialogStage.setScene(new Scene(vbox));
			    dialogStage.show();
				conn.close();
			} 
			catch (Exception e) 
			{ 
				Stage dialogStage = new Stage();
			    dialogStage.initModality(Modality.WINDOW_MODAL);

			    VBox vbox = new VBox(new Text("Enter valid data!!!"));
			    vbox.setAlignment(Pos.CENTER);
			    vbox.setPadding(new Insets(15));
			    vbox.setPrefSize(400, 100);

			    dialogStage.setScene(new Scene(vbox));
			    dialogStage.show();
			} 
		}
	}
		
	
	public boolean check(int t1,String ffname,String llname,String mmno,String aadd)
	{
		if(t1<=0 || fname.getText().trim().isEmpty()|| lname.getText().trim().isEmpty() || mno.getText().trim().isEmpty() || add.getText().trim().isEmpty())
		{
			return false;
		}
		else
		{
			return true;
		}
		
	}
}
