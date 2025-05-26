package user_view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import dao.ClientDAO;
import model.Client;
import model.Tour;


public class SearchClientFrm extends JFrame implements ActionListener {
    private JTextField txtName, txtEmail, txtPhone;
    private JButton btnSearch, btnReset,btnAddNew;
    private JTable table;
    private DefaultTableModel tableModel;
    private Tour gobalTour;

    public SearchClientFrm(Tour tour) {
        super("Tìm kiếm Khách hàng");
        
        gobalTour = tour;

        this.setSize(800, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JLabel lblTitle = new JLabel("Tìm kiếm Khách hàng");
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(lblTitle);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        inputPanel.add(new JLabel("Tên"));
        txtName = new JTextField(10);
        inputPanel.add(txtName);

        inputPanel.add(new JLabel("Email"));
        txtEmail = new JTextField(10);
        inputPanel.add(txtEmail);

        inputPanel.add(new JLabel("SĐT"));
        txtPhone = new JTextField(10);
        inputPanel.add(txtPhone);

        btnSearch = new JButton("Search");
        btnSearch.addActionListener(this);

        btnReset = new JButton("Reset");
        btnReset.addActionListener(this);
        
        btnAddNew = new JButton("AddNew");
        btnAddNew.addActionListener(this);
        
        
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.add(btnReset);
        btnPanel.add(btnSearch);
        btnPanel.add(btnAddNew);

        mainPanel.add(inputPanel);
        mainPanel.add(btnPanel);
  

        String[] columnNames = { "Stt", "Tên", "Email", "SĐT", "Lựa chọn" };
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 4) return JPanel.class;
                return Object.class;
            }
        };

        table.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
        table.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox()));

        table.setRowHeight(30);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(780, 300));
        mainPanel.add(scrollPane);

        this.setContentPane(mainPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSearch) {
//        	lấy ra tour
        	Tour tour = (Tour) btnSearch.getClientProperty("tour");
        	
            tableModel.setRowCount(0);
            ClientDAO clientDAO = new ClientDAO();
            ArrayList<Client> resultList = clientDAO.searchClient(
                    txtName.getText(),
                    txtEmail.getText(),
                    txtPhone.getText()
            );

            int index = 1;
            for (Client client : resultList) {
                JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
                panelButtons.setPreferredSize(new Dimension(120, 25));

                JButton btnSelect = new JButton("Chọn");
                btnSelect.setFont(new Font("Arial", Font.PLAIN, 10));
                btnSelect.setPreferredSize(new Dimension(70, 20));
                btnSelect.putClientProperty("client", client);
                btnSelect.addActionListener(buttonActionListener);

                panelButtons.add(btnSelect);

                tableModel.addRow(new Object[]{
                        index++,
                        client.getName(),
                        client.getEmail(),
                        client.getPhoneNumber(),
                        panelButtons
                });
            }

        } else if (e.getSource() == btnReset) {
            txtName.setText("");
            txtEmail.setText("");
            txtPhone.setText("");
            tableModel.setRowCount(0);
        } else if(e.getSource() == btnAddNew) {
        	this.dispose();
        	(new AddClientFrm(gobalTour)).setVisible(true);
        }
    }

    private final ActionListener buttonActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton btn = (JButton) e.getSource();
            Client selectedClient = (Client) btn.getClientProperty("client");
            SearchClientFrm.this.setVisible(false);
            (new ConfirmBillFrm(gobalTour,selectedClient)).setVisible(true);
        }
    };

    class ButtonRenderer extends JPanel implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                      boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof JPanel) {
                return (JPanel) value;
            }
            return this;
        }
    }

    class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
        private JPanel panel;

        public ButtonEditor(JCheckBox checkBox) {
            panel = new JPanel();
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            if (value instanceof JPanel) {
                panel = (JPanel) value;
            }
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return panel;
        }
    }
}
