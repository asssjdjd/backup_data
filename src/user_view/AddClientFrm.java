package user_view;

import javax.swing.*;

import model.Tour;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import dao.ClientDAO;

public class AddClientFrm extends JFrame implements ActionListener{

    private JTextField txtName, txtAddress, txtPhone, txtEmail;
    private JTextArea txtNote;
    private JButton btnSave, btnReturn;
    private Tour tour;

    public AddClientFrm(Tour tour) {
        // Thiết lập tiêu đề và thuộc tính cơ bản của JFrame
        super("Thêm mới Khách hàng");
        this.setSize(400, 350); // Kích thước cửa sổ
        this.setLocationRelativeTo(null); // Căn giữa màn hình
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.tour = tour;

        // Tạo panel chính với bố cục dọc (BoxLayout)
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

        // Tiêu đề
        JLabel lblTitle = new JLabel("Thêm mới Khách hàng");
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT); // Căn giữa
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20)); // Font chữ
        mainPanel.add(lblTitle);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Khoảng cách

        // Panel chứa các trường nhập liệu
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2, 10, 10)); // Bố cục lưới 5 hàng, 2 cột

        // Trường "Tên"
        inputPanel.add(new JLabel("Tên:"));
        txtName = new JTextField();
        inputPanel.add(txtName);

        // Trường "Địa chỉ"
        inputPanel.add(new JLabel("Địa chỉ:"));
        txtAddress = new JTextField();
        inputPanel.add(txtAddress);

        // Trường "Số điện thoại"
        inputPanel.add(new JLabel("Số điện thoại:"));
        txtPhone = new JTextField();
        inputPanel.add(txtPhone);

        // Trường "Email"
        inputPanel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        inputPanel.add(txtEmail);

        // Trường "Lưu ý" (dùng JTextArea để nhập nhiều dòng)
        inputPanel.add(new JLabel("Lưu ý:"));
        txtNote = new JTextArea(3, 20); // 3 hàng, 20 cột
        txtNote.setLineWrap(true); // Tự động xuống dòng
        txtNote.setWrapStyleWord(true); // Xuống dòng theo từ
        JScrollPane noteScrollPane = new JScrollPane(txtNote); // Thêm thanh cuộn
        inputPanel.add(noteScrollPane);

        mainPanel.add(inputPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Khoảng cách

        // Panel chứa các nút
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnSave = new JButton("Lưu");
        btnReturn = new JButton("Quay lại");
        
        btnSave.addActionListener(this);
        btnReturn.addActionListener(this);


        buttonPanel.add(btnSave);
        buttonPanel.add(btnReturn);

        mainPanel.add(buttonPanel);

        // Đặt nội dung cho JFrame
        this.setContentPane(mainPanel);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
    	if(e.getSource() == btnReturn) {
    		this.dispose();
    		(new SearchClientFrm(tour)).setVisible(true);
    	}else {
    		ClientDAO clientDAO = new ClientDAO();
    		
    		boolean result = clientDAO.addClient(txtName.getText(), txtAddress.getText(), txtEmail.getText(), txtPhone.getText(), txtNote.getText());
    		if(result == true) {
    			JOptionPane.showMessageDialog(this, "Thêm khách hàng mới thành công");
    		}else {
    			JOptionPane.showMessageDialog(this, "Thêm khách hàng mới thất bại");
    		}
    		(new SearchClientFrm(tour)).setVisible(true);
    	}
    }

   
}