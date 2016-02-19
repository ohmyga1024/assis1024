package com.zhqiang.assis1024;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by zhqiang on 14-3-13.
 */
public class AssisUI extends JFrame{
    JTextField tfUrl = new JTextField(Props.getParams().get(Props.CL_URL));
    JTextField tfSearch = new JTextField();
    JComboBox sField = new JComboBox(new String[]{"Title","User"});
    JComboBox sForum = new JComboBox(new String[]{Props.ASIA_MOSAICKED,Props.ASIA_NONMOSAICKED,Props.A_COMIC});
    public static JLabel lInfo = new JLabel(" INFO...");
    JTextArea taShow = new JTextArea();

    public AssisUI(){
        init();
        this.setVisible(true);
        this.setSize(1200,700);
        this.setTitle("Assis1024 v1.0 by "+Assistant.myName);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void init(){
        lInfo.setForeground(Color.blue);
        JLabel lAD = new JLabel("  Mail: "+Assistant.myMail);
//        lAD.setForeground(Color.blue);
        JLabel lUrl = new JLabel("          Url:");
        JButton bGet = new JButton();
        Action getData = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String info = new Assistant_Jsoup().getData(tfUrl.getText());
                lInfo.setText(info);
            }
        };
        bGet.setAction(getData);
        bGet.setText("Get");

        JButton bSearch = new JButton();
        Action search = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                taShow.setText("");
                java.util.List<String> result = Assistant.search((String)(sForum.getSelectedItem()), (String)(sField.getSelectedItem()), tfSearch.getText());
                lInfo.setText("Result : "+result.size());
                for(String str : result){
                    taShow.append(str + "\n");
                }
            }
        };
        bSearch.setAction(search);
        bSearch.setText("Search");

        JButton bt_RemoveDuplicate = new JButton();
        Action removeDuplicate = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                lInfo.setText(Assistant.removeDuplicate());
            }
        };
        bt_RemoveDuplicate.setAction(removeDuplicate);
        bt_RemoveDuplicate.setText("RemoveDuplicates");


        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);

        this.add(lAD);
        this.add(lUrl);
        this.add(tfUrl);
        this.add(bGet);

        this.add(sForum);
        this.add(sField);
        this.add(tfSearch);
        this.add(bSearch);

        this.add(lInfo);
        this.add(bt_RemoveDuplicate);

        JScrollPane scrollPane = new JScrollPane(taShow);
        this.add(scrollPane);
        taShow.setEditable(false);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 1;
        c.weightx = 0;
        c.weighty = 0;
        layout.setConstraints(lAD, c);
        layout.setConstraints(lUrl, c);
        c.gridwidth = 5;
        c.weightx = 1;
        c.weighty = 0;
        layout.setConstraints(tfUrl, c);
        c.gridwidth = 0;
        c.weightx = 0;
        c.weighty = 0;
        layout.setConstraints(bGet, c);

        c.gridwidth = 1;
        c.weightx = 0;
        c.weighty = 0;
        layout.setConstraints(sForum, c);
        layout.setConstraints(sField, c);
        c.gridwidth = 5;
        c.weightx = 1;
        c.weighty = 0;
        layout.setConstraints(tfSearch, c);
        c.gridwidth = 0;
        c.weightx = 0;
        c.weighty = 0;
        layout.setConstraints(bSearch, c);

        c.gridwidth = 7;
        c.weightx = 1;
        c.weighty = 0;
        layout.setConstraints(lInfo, c);
        c.gridwidth = 0;
        c.weightx = 0;
        c.weighty = 0;
        layout.setConstraints(bt_RemoveDuplicate, c);

        c.gridwidth = 0;
        c.weightx = 1;
        c.weighty = 1;
        layout.setConstraints(scrollPane, c);
    }

    public static void main(String[] args){
        AssisUI ui = new AssisUI();
    }

}
