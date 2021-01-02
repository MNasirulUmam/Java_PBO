/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tugasPBO;

/**
 *
 * @author LENOVO
 */
import java.sql.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.UIManager.LookAndFeelInfo;

public class CRUD extends javax.swing.JFrame {
	//judul
	private JLabel judul;
	private JLabel soal;
	private JLabel a;
	private JLabel b;
        private JLabel c;
        private JLabel d;
        private JLabel e;
        private JLabel pilih;
	//field
	private JTextArea tsoal;
	private JTextField ta;
        private JTextField tb;
        private JTextField tc;
        private JTextField td;
        private JTextField te;
	private JComboBox tpilih;
	
	//tabel
	private DefaultTableModel data_tabel;
        private JTable tabelPanel;
        private JScrollPane skrol_tabel;
	
	//button
	private JButton btampil;
	private JButton binput;
	private JButton bubah;
	private JButton bexit;
	private JButton bdelete;

	//database
	private String id_soal;
	private String query;
        private Koneksidatabase db;
        private PreparedStatement pst;
        private Statement stat;
        private ResultSet rs;

	public CRUD(){
		this.setTitle("CRUD");
		this.setSize(520,530);

		JPanel contentPane = new JPanel(null);
		contentPane.setPreferredSize(new Dimension(520,630));
		contentPane.setBackground(new Color(192,192,192));

		judul = new JLabel();
		judul.setBounds(40,20,155,40);
		judul.setFont(new Font("SansSerif",0,20));
		judul.setText("Bank Soal");
		soal = new JLabel();
		soal.setBounds(40,90,90,35);
		soal.setText("Soal");
		a = new JLabel();
		a.setBounds(40,170,90,35);
		a.setText("A");
                b = new JLabel();
		b.setBounds(40,210,90,35);
		b.setText("B");
                c = new JLabel();
		c.setBounds(40,250,90,35);
		c.setText("C");
                d = new JLabel();
		d.setBounds(40,290,90,35);
		d.setText("D");
                e = new JLabel();
		e.setBounds(40,330,90,35);
		e.setText("E");
		pilih = new JLabel();
		pilih.setBounds(40,370,120,35);
		pilih.setText("Pilih");		

		tsoal = new JTextArea();
		tsoal.setBounds(100,90,350,75);
                ta = new JTextField();
		ta.setBounds(100,170,350,35);
                tb = new JTextField();
		tb.setBounds(100,210,350,35);
                tc = new JTextField();
		tc.setBounds(100,250,350,35);
                td = new JTextField();
		td.setBounds(100,290,350,35);
                te = new JTextField();
		te.setBounds(100,330,350,35);
		tpilih = new JComboBox();
		tpilih.setBounds(100,370,350,35);
		tpilih.addItem("A");
		tpilih.addItem("B");
		tpilih.addItem("C");
		tpilih.addItem("D");
		tpilih.addItem("E");

		String tableTitle[] = {"No","ID","Saol","A","B","C","D","E","Pilih"};
        data_tabel  = new DefaultTableModel(null,tableTitle);

        tabelPanel = new JTable();
        tabelPanel.setModel(data_tabel);
        tabelPanel.setEnabled(true);
        tabelPanel.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        skrol_tabel = new JScrollPane();
        skrol_tabel.getViewport().add(tabelPanel);
        skrol_tabel.setBounds(10,425,500,150);
        getDataTabel();

		btampil = new JButton();
		btampil.setBounds(40,580,90,35);
		btampil.setText("Tampil");
		btampil.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                getDataTabel();
            }
        });

		binput = new JButton();
		binput.setBounds(150,580,90,35);
		binput.setText("Input");
		binput.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                save_data(evt);
                getDataTabel();
            }
        });

		bubah = new JButton();
		bubah.setBounds(260,580,90,35);
		bubah.setText("Ubah");
		bubah.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                update_data(evt);
                getDataTabel();
            }
        });


		bexit = new JButton();
		bexit.setBounds(370,580,90,35);
		bexit.setText("Exit");
		bexit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                dispose();
            }
        });

		bdelete = new JButton();
		bdelete.setBounds(460,370,35,35);
		bdelete.setText("X");
		bdelete.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                delete_data(evt);
                getDataTabel();
            }
        });

		contentPane.add(judul);
		contentPane.add(soal);
		contentPane.add(a);
                contentPane.add(b);
                contentPane.add(c);
                contentPane.add(d);
                contentPane.add(e);
		contentPane.add(pilih);

		contentPane.add(tsoal);
		contentPane.add(ta);
                contentPane.add(tb);
                contentPane.add(tc);
                contentPane.add(td);
                contentPane.add(te);
		contentPane.add(tpilih);

		contentPane.add(skrol_tabel,BorderLayout.CENTER);

		contentPane.add(btampil);
		contentPane.add(binput);
		contentPane.add(bubah);
		contentPane.add(bexit);
		contentPane.add(bdelete);
		
		this.add(contentPane);
		this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.pack();
		this.setVisible(true);
	}

	private void getDataTabel(){
        try{
            //get data from database
            db   = new Koneksidatabase();
            stat = db.koneksi.createStatement();
            rs   = stat.executeQuery("SELECT * FROM datasoal");
            int no = 1;
            data_tabel.setRowCount(0);
            while(rs.next()){
                String t_no 	= Integer.toString(no);
                String t_id 	= rs.getString("id");
                String t_soal  	= rs.getString("soal");
                String t_a = rs.getString("a");
                String t_b = rs.getString("b");
                String t_c = rs.getString("c");
                String t_d = rs.getString("d");
                String t_e = rs.getString("e");
                String t_pilih  = rs.getString("pilih");

                String[] t_data = {t_no, t_id, t_soal, t_a, t_b, t_c, t_d, t_e, t_pilih};
                data_tabel.addRow(t_data);
                no++;
            }
            rs.close();
            stat.close();
            db.koneksi.close();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void update_data (MouseEvent evt) {
        id_soal = JOptionPane.showInputDialog(null, "Input ID Soal ?");
        if(
        	tsoal.getText().isEmpty() &&
        	ta.getText().isEmpty() &&
                tb.getText().isEmpty() &&
                tc.getText().isEmpty() &&
                td.getText().isEmpty() &&
        	te.getText().isEmpty()
		){
			JOptionPane.showMessageDialog(null,"Please Input All Filed");
        }
        else {
            try{
            	query = "UPDATE datasoal SET `soal` = ?, `a` = ?, `b` = ?,`c` = ?,`d` = ?,`e` = ?,`pilih` = ? WHERE `id` = ?";
                db   = new Koneksidatabase();
                pst = db.koneksi.prepareStatement(query);
               	
               	pst.setString(1,tsoal.getText());
                pst.setString(2,ta.getText());
                pst.setString(3,tb.getText());
                pst.setString(4,tc.getText());
                pst.setString(5,td.getText());
                pst.setString(6,te.getText());
                pst.setString(7,tpilih.getSelectedItem().toString());
                pst.setString(8,id_soal);

                pst.execute();
                db.koneksi.close();
                JOptionPane.showMessageDialog(null, "Update Succsessfull");
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    private void save_data (MouseEvent evt) {
        if(
        	tsoal.getText().isEmpty() &&
        	ta.getText().isEmpty() &&
                tb.getText().isEmpty() &&
                tc.getText().isEmpty() &&
                td.getText().isEmpty() &&
        	te.getText().isEmpty()
		){
			JOptionPane.showMessageDialog(null,"Please Input All Filed");
        }
        else {
            try{
                query = "INSERT INTO datasoal (`soal`, `a`, `b`, `c`, `d`, `e`, `pilih`) VALUES(?,?,?,?,?,?,?)";
                db   = new Koneksidatabase();
                pst = db.koneksi.prepareStatement(query);
               	
               	pst.setString(1,tsoal.getText());
                pst.setString(2,ta.getText());
                pst.setString(3,tb.getText());
                pst.setString(4,tc.getText());
                pst.setString(5,td.getText());
                pst.setString(6,te.getText());
                pst.setString(7,tpilih.getSelectedItem().toString());

                pst.execute();
                db.koneksi.close();
                JOptionPane.showMessageDialog(null, "Save Succsessfull");
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    private void delete_data (MouseEvent evt) {
        id_soal = JOptionPane.showInputDialog(null, "Input ID Soal ?");
	    try{
	        //delete data from database
	        query = "DELETE FROM datasoal WHERE id = ?";
	        db   = new Koneksidatabase();
	        pst = db.koneksi.prepareStatement(query);
	        pst.setString(1,id_soal);
	        pst.execute();
	        db.koneksi.close();
	        JOptionPane.showMessageDialog(null, "Delete Succsessfull");
	    }
	    catch(Exception e)
	    {
	        JOptionPane.showMessageDialog(null, e);
	    }
    }

	public static void main(String[] args){
		System.setProperty("swing.defaultlaf", "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new CRUD();
			}
		});
	}
}
