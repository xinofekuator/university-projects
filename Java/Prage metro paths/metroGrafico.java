package practica;

/*Autores: Ignacio Amaya de la Peña
		   Adrián Cámara Caunedo
		   Borja Mas García
*/

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

//Clase y metodos para la creacion de la interfaz grafica de la aplicacion
public class metroGrafico extends JFrame {

	private JPanel contentPane;
	int contador=0;
	JButton[] botones= new JButton[49];
	JButton inicio;
	JButton fin;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					metroGrafico frame = new metroGrafico();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public metroGrafico() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 852, 590);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JButton btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton boton = (JButton) e.getSource();
				if(contador!=2){
					if(boton.getBackground().equals(Color.blue)){
						boton.setBackground(Color.black);
						contador--;
					}
					else{
						boton.setBackground(Color.blue);
						if(contador==0){
							inicio=boton;
						}
						else{
							fin=boton;
						}

						contador++;

					}}}
		});
		btnNewButton.setBackground(Color.BLACK);
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.setBounds(26, 425, 10, 10);
		panel.add(btnNewButton);

		JButton button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton boton = (JButton) e.getSource();
				if(contador!=2){
					if(boton.getBackground().equals(Color.blue)){
						boton.setBackground(Color.black);
						contador--;
					}
					else{
						boton.setBackground(Color.blue);
						if(contador==0){
							inicio=boton;
						}
						else{
							fin=boton;
						}

						contador++;

					}}}
		});
		button.setForeground(Color.BLACK);
		button.setBackground(Color.BLACK);
		button.setBounds(65, 425, 10, 10);
		panel.add(button);

		JButton button_1 = new JButton("");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton boton = (JButton) e.getSource();
				if(contador!=2){
					if(boton.getBackground().equals(Color.blue)){
						boton.setBackground(Color.black);
						contador--;
					}
					else{
						boton.setBackground(Color.blue);
						if(contador==0){
							inicio=boton;
						}
						else{
							fin=boton;
						}

						contador++;

					}}}
		});
		button_1.setForeground(Color.BLACK);
		button_1.setBackground(Color.BLACK);
		button_1.setBounds(97, 425, 10, 10);
		panel.add(button_1);

		JButton button_2 = new JButton("");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton boton = (JButton) e.getSource();
				if(contador!=2){
					if(boton.getBackground().equals(Color.blue)){
						boton.setBackground(Color.black);
						contador--;
					}
					else{
						boton.setBackground(Color.blue);
						if(contador==0){
							inicio=boton;
						}
						else{
							fin=boton;
						}

						contador++;

					}}}
		});
		button_2.setForeground(Color.BLACK);
		button_2.setBackground(Color.BLACK);
		button_2.setBounds(128, 425, 10, 10);
		panel.add(button_2);

		JButton button_4 = new JButton("");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton boton = (JButton) e.getSource();
				if(contador!=2){
					if(boton.getBackground().equals(Color.blue)){
						boton.setBackground(Color.black);
						contador--;
					}
					else{
						boton.setBackground(Color.blue);
						if(contador==0){
							inicio=boton;
						}
						else{
							fin=boton;
						}

						contador++;

					}}}
		});
		button_4.setForeground(Color.BLACK);
		button_4.setBackground(Color.BLACK);
		button_4.setBounds(158, 400, 10, 10);
		panel.add(button_4);

		JButton button_5 = new JButton("");
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton boton = (JButton) e.getSource();
				if(contador!=2){
					if(boton.getBackground().equals(Color.blue)){
						boton.setBackground(Color.black);
						contador--;
					}
					else{
						boton.setBackground(Color.blue);
						if(contador==0){
							inicio=boton;
						}
						else{
							fin=boton;
						}

						contador++;

					}}}
		});
		button_5.setForeground(Color.BLACK);
		button_5.setBackground(Color.BLACK);
		button_5.setBounds(187, 400, 10, 10);
		panel.add(button_5);

		JButton button_6 = new JButton("");
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton boton = (JButton) e.getSource();
				if(contador!=2){
					if(boton.getBackground().equals(Color.blue)){
						boton.setBackground(Color.black);
						contador--;
					}
					else{
						boton.setBackground(Color.blue);
						if(contador==0){
							inicio=boton;
						}
						else{
							fin=boton;
						}

						contador++;

					}}}
		});
		button_6.setForeground(Color.BLACK);
		button_6.setBackground(Color.BLACK);
		button_6.setBounds(225, 400, 10, 10);
		panel.add(button_6);

		JButton button_7 = new JButton("");
		button_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton boton = (JButton) e.getSource();
				if(contador!=2){
					if(boton.getBackground().equals(Color.blue)){
						boton.setBackground(Color.black);
						contador--;
					}
					else{
						boton.setBackground(Color.blue);
						if(contador==0){
							inicio=boton;
						}
						else{
							fin=boton;
						}

						contador++;

					}}}
		});
		button_7.setForeground(Color.BLACK);
		button_7.setBackground(Color.BLACK);
		button_7.setBounds(260, 400, 10, 10);
		panel.add(button_7);

		JButton button_8 = new JButton("");
		button_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton boton = (JButton) e.getSource();
				if(contador!=2){
					if(boton.getBackground().equals(Color.blue)){
						boton.setBackground(Color.black);
						contador--;
					}
					else{
						boton.setBackground(Color.blue);
						if(contador==0){
							inicio=boton;
						}
						else{
							fin=boton;
						}

						contador++;

					}}}
		});
		button_8.setForeground(Color.BLACK);
		button_8.setBackground(Color.BLACK);
		button_8.setBounds(290, 369, 10, 10);
		panel.add(button_8);

		JButton button_9 = new JButton("");
		button_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton boton = (JButton) e.getSource();
				if(contador!=2){
					if(boton.getBackground().equals(Color.blue)){
						boton.setBackground(Color.black);
						contador--;
					}
					else{
						boton.setBackground(Color.blue);
						if(contador==0){
							inicio=boton;
						}
						else{
							fin=boton;
						}

						contador++;

					}}}
		});
		button_9.setForeground(Color.BLACK);
		button_9.setBackground(Color.BLACK);
		button_9.setBounds(290, 337, 10, 10);
		panel.add(button_9);

		JButton button_10 = new JButton("");
		button_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton boton = (JButton) e.getSource();
				if(contador!=2){
					if(boton.getBackground().equals(Color.blue)){
						boton.setBackground(Color.black);
						contador--;
					}
					else{
						boton.setBackground(Color.blue);
						if(contador==0){
							inicio=boton;
						}
						else{
							fin=boton;
						}

						contador++;

					}}}
		});
		button_10.setForeground(Color.BLACK);
		button_10.setBackground(Color.BLACK);
		button_10.setBounds(326, 305, 10, 10);
		panel.add(button_10);

		JButton button_11 = new JButton("");
		button_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton boton = (JButton) e.getSource();
				if(contador!=2){
					if(boton.getBackground().equals(Color.blue)){
						boton.setBackground(Color.black);
						contador--;
					}
					else{
						boton.setBackground(Color.blue);
						if(contador==0){
							inicio=boton;
						}
						else{
							fin=boton;
						}

						contador++;

					}}}
		});
		button_11.setForeground(Color.BLACK);
		button_11.setBackground(Color.BLACK);
		button_11.setBounds(336, 264, 10, 10);
		panel.add(button_11);

		JButton button_12 = new JButton("");
		button_12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton boton = (JButton) e.getSource();
				if(contador!=2){
					if(boton.getBackground().equals(Color.blue)){
						boton.setBackground(Color.black);
						contador--;
					}
					else{
						boton.setBackground(Color.blue);
						if(contador==0){
							inicio=boton;
						}
						else{
							fin=boton;
						}

						contador++;

					}}}
		});
		button_12.setForeground(Color.BLACK);
		button_12.setBackground(Color.BLACK);
		button_12.setBounds(356, 243, 10, 10);
		panel.add(button_12);

		JButton button_13 = new JButton("");
		button_13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton boton = (JButton) e.getSource();
				if(contador!=2){
					if(boton.getBackground().equals(Color.blue)){
						boton.setBackground(Color.black);
						contador--;
					}
					else{
						boton.setBackground(Color.blue);
						if(contador==0){
							inicio=boton;
						}
						else{
							fin=boton;
						}

						contador++;

					}}}
		});
		button_13.setForeground(Color.BLACK);
		button_13.setBackground(Color.BLACK);
		button_13.setBounds(326, 218, 10, 10);
		panel.add(button_13);

		JButton button_14 = new JButton("");
		button_14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton boton = (JButton) e.getSource();
				if(contador!=2){
					if(boton.getBackground().equals(Color.blue)){
						boton.setBackground(Color.black);
						contador--;
					}
					else{
						boton.setBackground(Color.blue);
						if(contador==0){
							inicio=boton;
						}
						else{
							fin=boton;
						}

						contador++;

					}}}
		});
		button_14.setForeground(Color.BLACK);
		button_14.setBackground(Color.BLACK);
		button_14.setBounds(290, 206, 10, 10);
		panel.add(button_14);

		JButton button_15 = new JButton("");
		button_15.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton boton = (JButton) e.getSource();
				if(contador!=2){
					if(boton.getBackground().equals(Color.blue)){
						boton.setBackground(Color.black);
						contador--;
					}
					else{
						boton.setBackground(Color.blue);
						if(contador==0){
							inicio=boton;
						}
						else{
							fin=boton;
						}

						contador++;

					}}}
		});
		button_15.setForeground(Color.BLACK);
		button_15.setBackground(Color.BLACK);
		button_15.setBounds(275, 189, 10, 10);
		panel.add(button_15);

		JButton button_16 = new JButton("");
		button_16.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton boton = (JButton) e.getSource();
				if(contador!=2){
					if(boton.getBackground().equals(Color.blue)){
						boton.setBackground(Color.black);
						contador--;
					}
					else{
						boton.setBackground(Color.blue);
						if(contador==0){
							inicio=boton;
						}
						else{
							fin=boton;
						}

						contador++;

					}}}
		});
		button_16.setForeground(Color.BLACK);
		button_16.setBackground(Color.BLACK);
		button_16.setBounds(241, 178, 10, 10);
		panel.add(button_16);

		JButton button_17 = new JButton("");
		button_17.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton boton = (JButton) e.getSource();
				if(contador!=2){
					if(boton.getBackground().equals(Color.blue)){
						boton.setBackground(Color.black);
						contador--;
					}
					else{
						boton.setBackground(Color.blue);
						if(contador==0){
							inicio=boton;
						}
						else{
							fin=boton;
						}

						contador++;

					}}}
		});
		button_17.setForeground(Color.BLACK);
		button_17.setBackground(Color.BLACK);
		button_17.setBounds(386, 274, 10, 10);
		panel.add(button_17);

		JButton button_18 = new JButton("");
		button_18.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton boton = (JButton) e.getSource();
				if(contador!=2){
					if(boton.getBackground().equals(Color.blue)){
						boton.setBackground(Color.black);
						contador--;
					}
					else{
						boton.setBackground(Color.blue);
						if(contador==0){
							inicio=boton;
						}
						else{
							fin=boton;
						}

						contador++;

					}}}
		});
		button_18.setForeground(Color.BLACK);
		button_18.setBackground(Color.BLACK);
		button_18.setBounds(386, 369, 10, 10);
		panel.add(button_18);

		JButton button_19 = new JButton("");
		button_19.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton boton = (JButton) e.getSource();
				if(contador!=2){
					if(boton.getBackground().equals(Color.blue)){
						boton.setBackground(Color.black);
						contador--;
					}
					else{
						boton.setBackground(Color.blue);
						if(contador==0){
							inicio=boton;
						}
						else{
							fin=boton;
						}

						contador++;

					}}}
		});
		button_19.setForeground(Color.BLACK);
		button_19.setBackground(Color.BLACK);
		button_19.setBounds(386, 316, 10, 10);
		panel.add(button_19);

		JButton button_20 = new JButton("");
		button_20.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton boton = (JButton) e.getSource();
				if(contador!=2){
					if(boton.getBackground().equals(Color.blue)){
						boton.setBackground(Color.black);
						contador--;
					}
					else{
						boton.setBackground(Color.blue);
						if(contador==0){
							inicio=boton;
						}
						else{
							fin=boton;
						}

						contador++;

					}}}
		});
		button_20.setForeground(Color.BLACK);
		button_20.setBackground(Color.BLACK);
		button_20.setBounds(386, 400, 10, 10);
		panel.add(button_20);

		JButton button_21 = new JButton("");
		button_21.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton boton = (JButton) e.getSource();
				if(contador!=2){
					if(boton.getBackground().equals(Color.blue)){
						boton.setBackground(Color.black);
						contador--;
					}
					else{
						boton.setBackground(Color.blue);
						if(contador==0){
							inicio=boton;
						}
						else{
							fin=boton;
						}

						contador++;

					}}}
		});
		button_21.setForeground(Color.BLACK);
		button_21.setBackground(Color.BLACK);
		button_21.setBounds(411, 439, 10, 10);
		panel.add(button_21);

		JButton button_22 = new JButton("");
		button_22.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton boton = (JButton) e.getSource();
				if(contador!=2){
					if(boton.getBackground().equals(Color.blue)){
						boton.setBackground(Color.black);
						contador--;
					}
					else{
						boton.setBackground(Color.blue);
						if(contador==0){
							inicio=boton;
						}
						else{
							fin=boton;
						}

						contador++;

					}}}
		});
		button_22.setForeground(Color.BLACK);
		button_22.setBackground(Color.BLACK);
		button_22.setBounds(435, 464, 10, 10);
		panel.add(button_22);

		JButton button_23 = new JButton("");
		button_23.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton boton = (JButton) e.getSource();
				if(contador!=2){
					if(boton.getBackground().equals(Color.blue)){
						boton.setBackground(Color.black);
						contador--;
					}
					else{
						boton.setBackground(Color.blue);
						if(contador==0){
							inicio=boton;
						}
						else{
							fin=boton;
						}

						contador++;

					}}}
		});
		button_23.setForeground(Color.BLACK);
		button_23.setBackground(Color.BLACK);
		button_23.setBounds(466, 495, 10, 10);
		panel.add(button_23);

		JButton button_24 = new JButton("");
		button_24.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton boton = (JButton) e.getSource();
				if(contador!=2){
					if(boton.getBackground().equals(Color.blue)){
						boton.setBackground(Color.black);
						contador--;
					}
					else{
						boton.setBackground(Color.blue);
						if(contador==0){
							inicio=boton;
						}
						else{
							fin=boton;
						}

						contador++;

					}}}
		});
		button_24.setForeground(Color.BLACK);
		button_24.setBackground(Color.BLACK);
		button_24.setBounds(506, 510, 10, 10);
		panel.add(button_24);

		JButton button_25 = new JButton("");
		button_25.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton boton = (JButton) e.getSource();
				if(contador!=2){
					if(boton.getBackground().equals(Color.blue)){
						boton.setBackground(Color.black);
						contador--;
					}
					else{
						boton.setBackground(Color.blue);
						if(contador==0){
							inicio=boton;
						}
						else{
							fin=boton;
						}

						contador++;

					}}}
		});
		button_25.setForeground(Color.BLACK);
		button_25.setBackground(Color.BLACK);
		button_25.setBounds(548, 510, 10, 10);
		panel.add(button_25);

		JButton button_26 = new JButton("");
		button_26.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton boton = (JButton) e.getSource();
				if(contador!=2){
					if(boton.getBackground().equals(Color.blue)){
						boton.setBackground(Color.black);
						contador--;
					}
					else{
						boton.setBackground(Color.blue);
						if(contador==0){
							inicio=boton;
						}
						else{
							fin=boton;
						}

						contador++;

					}}}
		});
		button_26.setForeground(Color.BLACK);
		button_26.setBackground(Color.BLACK);
		button_26.setBounds(590, 510, 10, 10);
		panel.add(button_26);

		JButton button_27 = new JButton("");
		button_27.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton boton = (JButton) e.getSource();
				if(contador!=2){
					if(boton.getBackground().equals(Color.blue)){
						boton.setBackground(Color.black);
						contador--;
					}
					else{
						boton.setBackground(Color.blue);
						if(contador==0){
							inicio=boton;
						}
						else{
							fin=boton;
						}

						contador++;

					}}}
		});
		button_27.setForeground(Color.BLACK);
		button_27.setBackground(Color.BLACK);
		button_27.setBounds(632, 510, 10, 10);
		panel.add(button_27);

		JButton button_28 = new JButton("");
		button_28.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton boton = (JButton) e.getSource();
				if(contador!=2){
					if(boton.getBackground().equals(Color.blue)){
						boton.setBackground(Color.black);
						contador--;
					}
					else{
						boton.setBackground(Color.blue);
						if(contador==0){
							inicio=boton;
						}
						else{
							fin=boton;
						}

						contador++;

					}}}
		});
		button_28.setForeground(Color.BLACK);
		button_28.setBackground(Color.BLACK);
		button_28.setBounds(368, 228, 10, 10);
		panel.add(button_28);

		JButton button_29 = new JButton("");
		button_29.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton boton = (JButton) e.getSource();
				if(contador!=2){
					if(boton.getBackground().equals(Color.blue)){
						boton.setBackground(Color.black);
						contador--;
					}
					else{
						boton.setBackground(Color.blue);
						if(contador==0){
							inicio=boton;
						}
						else{
							fin=boton;
						}

						contador++;

					}}}
		});
		button_29.setForeground(Color.BLACK);
		button_29.setBackground(Color.BLACK);
		button_29.setBounds(386, 243, 10, 10);
		panel.add(button_29);

		JButton button_30 = new JButton("");
		button_30.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton boton = (JButton) e.getSource();
				if(contador!=2){
					if(boton.getBackground().equals(Color.blue)){
						boton.setBackground(Color.black);
						contador--;
					}
					else{
						boton.setBackground(Color.blue);
						if(contador==0){
							inicio=boton;
						}
						else{
							fin=boton;
						}

						contador++;

					}}}
		});
		button_30.setForeground(Color.BLACK);
		button_30.setBackground(Color.BLACK);
		button_30.setBounds(386, 166, 10, 10);
		panel.add(button_30);

		JButton button_31 = new JButton("");
		button_31.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton boton = (JButton) e.getSource();
				if(contador!=2){
					if(boton.getBackground().equals(Color.blue)){
						boton.setBackground(Color.black);
						contador--;
					}
					else{
						boton.setBackground(Color.blue);
						if(contador==0){
							inicio=boton;
						}
						else{
							fin=boton;
						}

						contador++;

					}}}
		});
		button_31.setForeground(Color.BLACK);
		button_31.setBackground(Color.BLACK);
		button_31.setBounds(386, 125, 10, 10);
		panel.add(button_31);

		JButton button_32 = new JButton("");
		button_32.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton boton = (JButton) e.getSource();
				if(contador!=2){
					if(boton.getBackground().equals(Color.blue)){
						boton.setBackground(Color.black);
						contador--;
					}
					else{
						boton.setBackground(Color.blue);
						if(contador==0){
							inicio=boton;
						}
						else{
							fin=boton;
						}
						contador++;
					}}}
		});
		button_32.setForeground(Color.BLACK);
		button_32.setBackground(Color.BLACK);
		button_32.setBounds(422, 26, 10, 10);
		panel.add(button_32);

		JButton button_33 = new JButton("");
		button_33.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton boton = (JButton) e.getSource();
				if(contador!=2){
					if(boton.getBackground().equals(Color.blue)){
						boton.setBackground(Color.black);
						contador--;
					}
					else{
						boton.setBackground(Color.blue);
						if(contador==0){
							inicio=boton;
						}
						else{
							fin=boton;
						}

						contador++;

					}}}
		});
		button_33.setForeground(Color.BLACK);
		button_33.setBackground(Color.BLACK);
		button_33.setBounds(476, 26, 10, 10);
		panel.add(button_33);

		JButton button_34 = new JButton("");
		button_34.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton boton = (JButton) e.getSource();
				if(contador!=2){
					if(boton.getBackground().equals(Color.blue)){
						boton.setBackground(Color.black);
						contador--;
					}
					else{
						boton.setBackground(Color.blue);
						if(contador==0){
							inicio=boton;
						}
						else{
							fin=boton;
						}

						contador++;

					}}}
		});
		button_34.setForeground(Color.BLACK);
		button_34.setBackground(Color.BLACK);
		button_34.setBounds(386, 206, 10, 10);
		panel.add(button_34);

		JButton button_35 = new JButton("");
		button_35.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton boton = (JButton) e.getSource();
				if(contador!=2){
					if(boton.getBackground().equals(Color.blue)){
						boton.setBackground(Color.black);
						contador--;
					}
					else{
						boton.setBackground(Color.blue);
						if(contador==0){
							inicio=boton;
						}
						else{
							fin=boton;
						}

						contador++;

					}}}
		});
		button_35.setForeground(Color.BLACK);
		button_35.setBackground(Color.BLACK);
		button_35.setBounds(422, 305, 10, 10);
		panel.add(button_35);

		JButton button_36 = new JButton("");
		button_36.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton boton = (JButton) e.getSource();
				if(contador!=2){
					if(boton.getBackground().equals(Color.blue)){
						boton.setBackground(Color.black);
						contador--;
					}
					else{
						boton.setBackground(Color.blue);
						if(contador==0){
							inicio=boton;
						}
						else{
							fin=boton;
						}

						contador++;

					}}}
		});
		button_36.setForeground(Color.BLACK);
		button_36.setBackground(Color.BLACK);
		button_36.setBounds(455, 305, 10, 10);
		panel.add(button_36);

		JButton button_37 = new JButton("");
		button_37.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton boton = (JButton) e.getSource();
				if(contador!=2){
					if(boton.getBackground().equals(Color.blue)){
						boton.setBackground(Color.black);
						contador--;
					}
					else{
						boton.setBackground(Color.blue);
						if(contador==0){
							inicio=boton;
						}
						else{
							fin=boton;
						}

						contador++;

					}}}
		});
		button_37.setForeground(Color.BLACK);
		button_37.setBackground(Color.BLACK);
		button_37.setBounds(492, 305, 10, 10);
		panel.add(button_37);

		JButton button_38 = new JButton("");
		button_38.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton boton = (JButton) e.getSource();
				if(contador!=2){
					if(boton.getBackground().equals(Color.blue)){
						boton.setBackground(Color.black);
						contador--;
					}
					else{
						boton.setBackground(Color.blue);
						if(contador==0){
							inicio=boton;
						}
						else{
							fin=boton;
						}

						contador++;

					}}}
		});
		button_38.setForeground(Color.BLACK);
		button_38.setBackground(Color.BLACK);
		button_38.setBounds(528, 305, 10, 10);
		panel.add(button_38);

		JButton button_39 = new JButton("");
		button_39.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton boton = (JButton) e.getSource();
				if(contador!=2){
					if(boton.getBackground().equals(Color.blue)){
						boton.setBackground(Color.black);
						contador--;
					}
					else{
						boton.setBackground(Color.blue);
						if(contador==0){
							inicio=boton;
						}
						else{
							fin=boton;
						}

						contador++;

					}}}
		});
		button_39.setForeground(Color.BLACK);
		button_39.setBackground(Color.BLACK);
		button_39.setBounds(561, 337, 10, 10);
		panel.add(button_39);

		JButton button_40 = new JButton("");
		button_40.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton boton = (JButton) e.getSource();
				if(contador!=2){
					if(boton.getBackground().equals(Color.blue)){
						boton.setBackground(Color.black);
						contador--;
					}
					else{
						boton.setBackground(Color.blue);
						if(contador==0){
							inicio=boton;
						}
						else{
							fin=boton;
						}

						contador++;

					}}}
		});
		button_40.setForeground(Color.BLACK);
		button_40.setBackground(Color.BLACK);
		button_40.setBounds(590, 369, 10, 10);
		panel.add(button_40);

		JButton button_41 = new JButton("");
		button_41.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton boton = (JButton) e.getSource();
				if(contador!=2){
					if(boton.getBackground().equals(Color.blue)){
						boton.setBackground(Color.black);
						contador--;
					}
					else{
						boton.setBackground(Color.blue);
						if(contador==0){
							inicio=boton;
						}
						else{
							fin=boton;
						}

						contador++;

					}}}
		});
		button_41.setForeground(Color.BLACK);
		button_41.setBackground(Color.BLACK);
		button_41.setBounds(466, 206, 10, 10);
		panel.add(button_41);

		JButton button_42 = new JButton("");
		button_42.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton boton = (JButton) e.getSource();
				if(contador!=2){
					if(boton.getBackground().equals(Color.blue)){
						boton.setBackground(Color.black);
						contador--;
					}
					else{
						boton.setBackground(Color.blue);
						if(contador==0){
							inicio=boton;
						}
						else{
							fin=boton;
						}

						contador++;

					}}}
		});
		button_42.setForeground(Color.BLACK);
		button_42.setBackground(Color.BLACK);
		button_42.setBounds(435, 206, 10, 10);
		panel.add(button_42);

		JButton button_43 = new JButton("");
		button_43.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton boton = (JButton) e.getSource();
				if(contador!=2){
					if(boton.getBackground().equals(Color.blue)){
						boton.setBackground(Color.black);
						contador--;
					}
					else{
						boton.setBackground(Color.blue);
						if(contador==0){
							inicio=boton;
						}
						else{
							fin=boton;
						}

						contador++;

					}}}
		});
		button_43.setForeground(Color.BLACK);
		button_43.setBackground(Color.BLACK);
		button_43.setBounds(548, 189, 10, 10);
		panel.add(button_43);

		JButton button_44 = new JButton("");
		button_44.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton boton = (JButton) e.getSource();
				if(contador!=2){
					if(boton.getBackground().equals(Color.blue)){
						boton.setBackground(Color.black);
						contador--;
					}
					else{
						boton.setBackground(Color.blue);
						if(contador==0){
							inicio=boton;
						}
						else{
							fin=boton;
						}

						contador++;

					}}}
		});
		button_44.setForeground(Color.BLACK);
		button_44.setBackground(Color.BLACK);
		button_44.setBounds(561, 166, 10, 10);
		panel.add(button_44);

		JButton button_45 = new JButton("");
		button_45.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton boton = (JButton) e.getSource();
				if(contador!=2){
					if(boton.getBackground().equals(Color.blue)){
						boton.setBackground(Color.black);
						contador--;
					}
					else{
						boton.setBackground(Color.blue);
						if(contador==0){
							inicio=boton;
						}
						else{
							fin=boton;
						}

						contador++;

					}}}
		});
		button_45.setForeground(Color.BLACK);
		button_45.setBackground(Color.BLACK);
		button_45.setBounds(621, 189, 10, 10);
		panel.add(button_45);

		JButton button_46 = new JButton("");
		button_46.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton boton = (JButton) e.getSource();
				if(contador!=2){
					if(boton.getBackground().equals(Color.blue)){
						boton.setBackground(Color.black);
						contador--;
					}
					else{
						boton.setBackground(Color.blue);
						if(contador==0){
							inicio=boton;
						}
						else{
							fin=boton;
						}

						contador++;

					}}}
		});
		button_46.setForeground(Color.BLACK);
		button_46.setBackground(Color.BLACK);
		button_46.setBounds(668, 189, 10, 10);
		panel.add(button_46);

		JButton button_47 = new JButton("");
		button_47.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton boton = (JButton) e.getSource();
				if(contador!=2){
					if(boton.getBackground().equals(Color.blue)){
						boton.setBackground(Color.black);
						contador--;
					}
					else{
						boton.setBackground(Color.blue);
						if(contador==0){
							inicio=boton;
						}
						else{
							fin=boton;
						}

						contador++;

					}}}
		});
		button_47.setForeground(Color.BLACK);
		button_47.setBackground(Color.BLACK);
		button_47.setBounds(711, 189, 10, 10);
		panel.add(button_47);

		JButton button_48 = new JButton("");
		button_48.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton boton = (JButton) e.getSource();
				if(contador!=2){
					if(boton.getBackground().equals(Color.blue)){
						boton.setBackground(Color.black);
						contador--;
					}
					else{
						boton.setBackground(Color.blue);
						if(contador==0){
							inicio=boton;
						}
						else{
							fin=boton;
						}

						contador++;

					}}}
		});
		button_48.setForeground(Color.BLACK);
		button_48.setBackground(Color.BLACK);
		button_48.setBounds(506, 189, 10, 10);
		panel.add(button_48);

		botones[0]=btnNewButton;
		botones[1]=button;
		botones[2]=button_1;
		botones[3]=button_2;
		botones[4]=button_4;
		botones[5]=button_5;
		botones[6]=button_6;
		botones[7]=button_7;
		botones[8]=button_8;
		botones[9]=button_9;
		botones[10]=button_10;
		botones[11]=button_11;
		botones[12]=button_12;
		botones[13]=button_28;
		botones[14]=button_34;
		botones[15]=button_42;
		botones[16]=button_41;
		botones[17]=button_48;
		botones[18]=button_43;
		botones[19]=button_44;
		botones[20]=button_45;
		botones[21]=button_46;
		botones[22]=button_47;
		botones[23]=button_16;
		botones[24]=button_15;
		botones[25]=button_14;
		botones[26]=button_13;
		botones[27]=button_17;
		botones[28]=button_35;
		botones[29]=button_36;
		botones[30]=button_37;
		botones[31]=button_38;
		botones[32]=button_39;
		botones[33]=button_40;
		botones[34]=button_33;
		botones[35]=button_32;
		botones[36]=button_31;
		botones[37]=button_30;
		botones[38]=button_29;
		botones[39]=button_19;
		botones[40]=button_18;
		botones[41]=button_20;
		botones[42]=button_21;
		botones[43]=button_22;
		botones[44]=button_23;
		botones[45]=button_24;
		botones[46]=button_25;
		botones[47]=button_26;
		botones[48]=button_27;

		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(contador==2){
					Datos d=new Datos();
					Recorrido r=new Recorrido();
					ArrayList<ParadaMetro> resultado=r.calcularRecorrido(d.getParadas()[buscarBoton(inicio)],d.getParadas()[buscarBoton(fin)],d.getH());
					for(ParadaMetro p: resultado){
						botones[p.getId()].setBackground(Color.cyan);
					}
					System.out.println("Tiempo de trayecto: " + r.g + " segundos");
				}
			}
		});
		btnOk.setBounds(632, 451, 89, 23);
		panel.add(btnOk);

		JButton btnRestart = new JButton("Restart");
		btnRestart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(JButton b:botones){
					b.setBackground(Color.black);
				}
				contador=0;
			}
		});
		btnRestart.setBounds(632, 387, 89, 23);
		panel.add(btnRestart);
		JLabel label = new JLabel("");
		label.setBounds(10, 11, 730, 546);
		label.setIcon(new ImageIcon(getClass().getResource("metro praga.jpg")));
		panel.add(label);
	}
	private int buscarBoton(JButton boton){
		boolean encontrado = false;
		int i = 0;
		while(!encontrado){
			if(botones[i]==boton)encontrado=true;
			else{
				i++;
			}
		}
		return i;
	}

}
