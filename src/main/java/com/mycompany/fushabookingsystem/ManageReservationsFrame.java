package com.mycompany.fushabookingsystem;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ManageReservationsFrame extends JFrame {

    private final JTable tblReservations;
    private final JTextField txtReservationID = new JTextField();
    private final JTextField txtStudentID = new JTextField();

    private final JComboBox<String> cmbStudySpace = new JComboBox<>(
        new String[]{"اختر مكانًا"}
    );

    private final JTextField txtReservationDate = new JTextField();
    private final JTextField txtStartTime = new JTextField();
    private final JTextField txtEndTime = new JTextField();

    private final Color navy = new Color(48, 59, 145);
    private final Color teal = new Color(32, 163, 166);
    private final Font font = new Font("Segoe UI", Font.PLAIN, 15);

    public ManageReservationsFrame() {

        setTitle("FUSHA - Manage Reservations");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout(12, 14));
        root.setBackground(new Color(245, 250, 255));
        root.setBorder(BorderFactory.createEmptyBorder(18, 26, 18, 26));
        setContentPane(root);

        JLabel title = new JLabel(
            "عرض وتعديل الحجوزات",
            SwingConstants.CENTER
        );

        title.setForeground(navy);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        root.add(title, BorderLayout.NORTH);

        DefaultTableModel model = new DefaultTableModel(
            new String[]{
                "رقم الحجز",
                "الرقم الجامعي",
                "المكان",
                "التاريخ",
                "وقت البداية",
                "وقت النهاية"
            }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        tblReservations = new JTable(model);
        tblReservations.setFont(font);
        tblReservations.setRowHeight(27);

        tblReservations.getTableHeader().setFont(
            new Font("Segoe UI", Font.BOLD, 14)
        );

        JPanel tableArea = new JPanel(new BorderLayout(5, 6));
        tableArea.setOpaque(false);

        tableArea.add(
            new JScrollPane(tblReservations),
            BorderLayout.CENTER
        );

        JButton btnViewReservations = button("عرض الحجوزات", teal);
        JButton btnSelectReservation = button("اختيار الحجز", teal);

        JPanel tableActions = new JPanel(
            new FlowLayout(FlowLayout.CENTER, 15, 4)
        );

        tableActions.setOpaque(false);
        tableActions.add(btnViewReservations);
        tableActions.add(btnSelectReservation);

        tableArea.add(tableActions, BorderLayout.SOUTH);
        root.add(tableArea, BorderLayout.CENTER);

        JPanel below = new JPanel(new BorderLayout(8, 10));
        below.setOpaque(false);

        JPanel fields = new JPanel(new GridLayout(3, 4, 10, 9));
        fields.setOpaque(false);

        fields.add(label("رقم الحجز"));
        fields.add(txtReservationID);

        fields.add(label("الرقم الجامعي"));
        fields.add(txtStudentID);

        fields.add(label("المكان"));
        fields.add(cmbStudySpace);

        fields.add(label("التاريخ YYYY-MM-DD"));
        fields.add(txtReservationDate);

        fields.add(label("وقت البداية HH:MM"));
        fields.add(txtStartTime);

        fields.add(label("وقت النهاية HH:MM"));
        fields.add(txtEndTime);

        txtReservationID.setEditable(false);

        below.add(fields, BorderLayout.CENTER);

        JPanel actions = new JPanel(
            new FlowLayout(FlowLayout.CENTER, 18, 6)
        );

        actions.setOpaque(false);

        JButton btnSaveChanges = button("حفظ التعديلات", teal);
        JButton btnBack = button("العودة للرئيسية", navy);

        actions.add(btnSaveChanges);
        actions.add(btnBack);

        below.add(actions, BorderLayout.SOUTH);
        root.add(below, BorderLayout.SOUTH);

        btnViewReservations.addActionListener(e -> pending());
        btnSaveChanges.addActionListener(e -> pending());

        btnSelectReservation.addActionListener(e -> {

            int row = tblReservations.getSelectedRow();

            if (row < 0) {
                JOptionPane.showMessageDialog(
                    this,
                    "اختاري حجزًا من الجدول أولًا."
                );
                return;
            }

            txtReservationID.setText(
                String.valueOf(tblReservations.getValueAt(row, 0))
            );

            txtStudentID.setText(
                String.valueOf(tblReservations.getValueAt(row, 1))
            );

            cmbStudySpace.setSelectedItem(
                String.valueOf(tblReservations.getValueAt(row, 2))
            );

            txtReservationDate.setText(
                String.valueOf(tblReservations.getValueAt(row, 3))
            );

            txtStartTime.setText(
                String.valueOf(tblReservations.getValueAt(row, 4))
            );

            txtEndTime.setText(
                String.valueOf(tblReservations.getValueAt(row, 5))
            );
        });

        btnBack.addActionListener(e -> {
            new MainDashboard().setVisible(true);
            dispose();
        });
    }

    private JLabel label(String text) {
        JLabel l = new JLabel(text, SwingConstants.RIGHT);
        l.setFont(font);
        l.setForeground(navy);
        return l;
    }

    private JButton button(String text, Color color) {
        JButton b = new JButton(text);
        b.setFont(new Font("Segoe UI", Font.BOLD, 15));
        b.setBackground(color);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setCursor(
            Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
        );
        return b;
    }

    private void pending() {
        JOptionPane.showMessageDialog(
            this,
            "هذه الواجهة جاهزة. عرض البيانات وحفظها يتطلب ربط Derby أولًا."
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
            new ManageReservationsFrame().setVisible(true)
        );
    }
}
