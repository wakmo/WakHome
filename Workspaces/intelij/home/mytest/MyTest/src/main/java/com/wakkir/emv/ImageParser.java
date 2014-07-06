package com.wakkir.emv;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Hashtable;


public class ImageParser extends JFrame implements TreeSelectionListener, ActionListener
{
    JTextArea image = new JTextArea(8, 100);
    TLV Root = new TLV();
    DefaultMutableTreeNode Tree = new DefaultMutableTreeNode(Root);
    JTree tree = new JTree(Tree);
    GridBagLayout pic = new GridBagLayout();
    JEditorPane viewPane = new JEditorPane();
    DefaultMutableTreeNode CurrentNode = null;
    Hashtable<String, String> TagName = new Hashtable<String, String>();
    JRadioButton ALU = new JRadioButton("ALU", true);
    JRadioButton SpanALU = new JRadioButton("SPAN ALU");
    JRadioButton MICAALU = new JRadioButton("MICA ALU");
    JRadioButton DGI = new JRadioButton("GP DGI");

    StringBuffer sb_details = new StringBuffer();
    StringBuffer sb_summary = new StringBuffer();

    public ImageParser()
    {
        super("Image Parser");
        setBounds(600, 300, 600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(pic);

        //create scrolling pane for image input
        image.setLineWrap(true);
        JScrollPane scroll = new JScrollPane(image);

        //Create a tree that allows one selection at a time.
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        //Listen for when the selection changes.
        tree.addTreeSelectionListener(this);
        tree.setShowsRootHandles(true);

        //Create the scroll pane and add the tree to it. 
        JScrollPane treeView = new JScrollPane(tree);

        //Set viewing pane to scroll
        viewPane.setEditable(false);
        JScrollPane details = new JScrollPane(viewPane);

        //add radio button group
        JPanel ButPanel = new JPanel();
        ButtonGroup group = new ButtonGroup();
        group.add(ALU);
        group.add(SpanALU);
        group.add(MICAALU);
        group.add(DGI);
        ButPanel.add(ALU);
        ButPanel.add(SpanALU);
        ButPanel.add(MICAALU);
        ButPanel.add(DGI);

        //Add the scroll panes to a split pane.
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setLeftComponent(treeView);
        splitPane.setRightComponent(details);
        Dimension minimumSize = new Dimension(100, 100);
        details.setMinimumSize(minimumSize);
        treeView.setMinimumSize(minimumSize);
        splitPane.setDividerLocation(200);
        splitPane.setPreferredSize(new Dimension(500, 300));

        // create the display
        JLabel imageLabel = new JLabel("Image: ");
        addComponent(imageLabel, 0, 0, 1, 1, 10, 10,
                GridBagConstraints.NONE, GridBagConstraints.EAST);
        addComponent(scroll, 1, 0, 9, 3, 90, 30,
                GridBagConstraints.BOTH, GridBagConstraints.WEST);
        addComponent(ButPanel, 1, 3, 3, 1, 30, 10,
                GridBagConstraints.BOTH, GridBagConstraints.WEST);
        JButton parse = new JButton("Parse");
        addComponent(parse, 8, 3, 2, 1, 20, 10,
                GridBagConstraints.NONE, GridBagConstraints.EAST);
        JButton export = new JButton("Export");
        addComponent(export, 0, 3, 2, 1, 20, 10,
                GridBagConstraints.NONE, GridBagConstraints.EAST);
        addComponent(splitPane, 0, 4, 10, 10, 100, 100,
                GridBagConstraints.BOTH, GridBagConstraints.CENTER);

        parse.addActionListener(this);
        export.addActionListener(this);
        tree.setRootVisible(false);

        setVisible(true);

        // store known EMV tags
        TagName.put("4F", "Application Identifier");
        TagName.put("50", "Application Label");
        TagName.put("57", "Track 2 Equivalent Data");
        TagName.put("5A", "PAN");
        TagName.put("5F20", "Cardholder Name");
        TagName.put("5F24", "Application Expiration Date");
        TagName.put("5F25", "Application Effective Date");
        TagName.put("5F28", "Issuer Country Code");
        TagName.put("5F30", "Service Code");
        TagName.put("5F34", "PSN");
        TagName.put("61", "Application Template");
        TagName.put("8C", "CDOL1");
        TagName.put("8D", "CDOL2");
        TagName.put("8E", "CVM");
        TagName.put("82", "AIP");
        TagName.put("8F", "CAPKI");
        TagName.put("90", "Issuer Public Key Certificate");
        TagName.put("92", "Issuer Public Key Remainder");
        TagName.put("93", "Signed Static Application Data");
        TagName.put("94", "Application File Locator");
        TagName.put("9F05", "Application Discretionary Data");
        TagName.put("9F07", "Application Usage Control");
        TagName.put("9F08", "Application Version Number");
        TagName.put("9F0B", "Cardholder Name - Extended");
        TagName.put("9F0D", "Issuer Action Code - Default");
        TagName.put("9F0E", "Issuer Action Code - Denial");
        TagName.put("9F0F", "Issuer Action Code - Online");
        TagName.put("9F14", "Lower Consecutive Offline Limit");
        TagName.put("9F1F", "Track 1 Discretionary Data");
        TagName.put("9F20", "Track 2 Discretionary Data");
        TagName.put("9F23", "Upper Consecutive Offline Limit");
        TagName.put("9F2D", "ICC PIN Encipherment Public Key Certificate");
        TagName.put("9F2E", "ICC PIN Encipherment Public Key Exponent");
        TagName.put("9F2F", "ICC Public Key Remainder");
        TagName.put("9F32", "Issuer Public Key Exponent");
        TagName.put("9F3B", "Application Reference Currency");
        TagName.put("9F42", "Application Currency Code");
        TagName.put("9F43", "Application Reference Currency Exponent");
        TagName.put("9F46", "ICC Public Key Certificate");
        TagName.put("9F47", "ICC Public Key Exponent");
        TagName.put("9F48", "ICC Public Key Remainder");
        TagName.put("9F49", "DDOL");
        TagName.put("5F2D", "Language Preference");
        TagName.put("65", "FCI Template");
        TagName.put("87", "Application Priority Indicator");
        TagName.put("9F10", "Issuer Application Data");
        TagName.put("9F11", "Issuer Code Table Index");
        TagName.put("9F12", "Application Preferred Name");
        TagName.put("9F19", "DDOL Terminal");
        TagName.put("9F38", "PDOL");
        TagName.put("9F44", "Application Currency Exponent");
        TagName.put("9F45", "Data Authentication Code");
        TagName.put("9F51", "Application Currency Code Card");
        TagName.put("9F52", "Application Default Action");
        TagName.put("9F53", "Consecutive Transaction Limit International");
        TagName.put("9F54", "Cumulative Total Transaction Amount Limit");
        TagName.put("9F55", "Geographic Indicator");
        TagName.put("9F56", "Issuer Authentication Indicator");
        TagName.put("9F57", "Issuer Country Code Card");
        TagName.put("9F58", "Lower Consecutive Offline Limit Card");
        TagName.put("9F59", "Upper Consecutive Offline Limit Card");
        TagName.put("9F5C", "Cumulative Total Transaction Amount Upper Limit");
        TagName.put("9F72", "Consecutive Transaction Limit International Country");
        TagName.put("9F73", "Currency Conversion Factor");
        TagName.put("9F74", "VLP Issuer Authorization Code");
        TagName.put("9F75", "Cumulative Total Transaction Amount Limit Dual Currency");
        TagName.put("9F76", "Secondary Application Currency Code");
        TagName.put("9F77", "VLP Funds Limit");
        TagName.put("9F78", "VLP Single Transaction Limit");
        TagName.put("9F79", "VLP Available Funds");
        TagName.put("70", "Record");
        TagName.put("9F4A", "SDA Tag List");
        TagName.put("61", "Dir");
        TagName.put("4F", "DF Name");
        TagName.put("6F", "FCI");
        TagName.put("84", "DF Name");
        TagName.put("A5", "FCI Proprietary");
        TagName.put("87", "Application Priority");
        TagName.put("5F2D", "Language Preference");
        TagName.put("9F11", "Issuer Code Table");


    }

    private void addComponent(
            Component component, int gridx, int gridy,
            int gridwidth, int gridheight, int weightx, int weighty, int fill,
            int anchor
    )
    {

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = gridx;
        constraints.gridy = gridy;
        constraints.gridwidth = gridwidth;
        constraints.gridheight = gridheight;
        constraints.weightx = weightx;
        constraints.weighty = weighty;
        constraints.fill = fill;
        constraints.anchor = anchor;
        pic.setConstraints(component, constraints);
        add(component);
    }


    public void actionPerformed(ActionEvent event)
    {
        int[] ALULength = {16, 4, 4, 4, 4, 4, 4};
        String[] ALUItem = {"MCD Number", "Code", "Data", "Dir", "FCI", "App Signature", "KTU Prime"};
        int[] SpanLength = {8, 32, 2, 4, 4};
        String[] SpanItem = {"Checksum", "AID", "Base App", "AIP", "Table Size"};


        // Parse button actioned
        System.out.println(event.getActionCommand());
        String currentTime = new Date().toString();


        if ("Export".equalsIgnoreCase(event.getActionCommand()))
        {

            System.out.println("\n" + currentTime + sb_summary.toString());
            System.out.println("\n" + currentTime + sb_details.toString());

            writeFile(".", "decoded-summary.txt", currentTime + "\n" + sb_summary.toString(), false);
            writeFile(".", "decoded-details.txt", currentTime + "\n" + sb_details.toString(), false);


        }
        else
        {
            //display root and clear tree
            tree.setRootVisible(true);
            Tree.removeAllChildren();
            tree.updateUI();

            String parseData = image.getText().toUpperCase();

            if (parseData == null || parseData.trim().length() == 0)
            {
                return;
            }

            // Check for valid data length
            if (parseData.length() % 2 != 0)
            {
                viewPane.setText("Odd number of characters in input data");
                return;
            }


            if (DGI.isSelected())
            {
                while (parseData.length() != 0)
                {
                    // get all high level DGIs or records
                    TLV RootBranch = new TLV();
                    parseData = Level1Parse(parseData, RootBranch);
                    if (RootBranch.Tag.substring(0, 1).equals("8"))
                    {
                        RootBranch.encrypted = true;
                    }
                    DefaultMutableTreeNode node = new DefaultMutableTreeNode(RootBranch);
                    sb_summary.append(RootBranch.printTLV());
                    //add high level nodes to root
                    Tree.add(node);

                    // parse the high leve for any lower level values
                    CurrentNode = node;
                    String parse2Data = RootBranch.Value;

                    sb_details.append("\n");
                    if (RootBranch.encrypted)
                    {
                        sb_details.append(RootBranch.printTL());
                        sb_details.append(" ");
                        sb_details.append(RootBranch.printV());
                    }
                    else
                    {
                        sb_details.append(RootBranch.printTL());
                    }


                    while (RootBranch.encrypted == false && parse2Data.length() != 0)
                    {
                        parse2Data = Level2Parse(parse2Data);
                    }

                }
            }
            else if (ALU.isSelected() || SpanALU.isSelected() || MICAALU.isSelected())
            {
                // deal with MCD Number, which doesn't have a length
                TLV MCDBranch = new TLV();
                MCDBranch.NodeName = ALUItem[0];
                MCDBranch.Value = parseData.substring(0, ALULength[0]);
                DefaultMutableTreeNode MCD = new DefaultMutableTreeNode(MCDBranch);
                Tree.add(MCD);

                //deal with remaining ALU items
                int StartChar = ALULength[0];
                for (int i = 1; i < ALULength.length; i++)
                {
                    TLV RootBranch = new TLV();
                    try
                    {
                        RootBranch.Length = parseData.substring(StartChar, StartChar + ALULength[i]);
                        RootBranch.NodeName = ALUItem[i];
                        int dataLength = 2 * Integer.parseInt(RootBranch.Length, 16);
                        RootBranch.Value = parseData.substring(StartChar + ALULength[i], StartChar + ALULength[i] + dataLength);
                        StartChar = StartChar + ALULength[i] + dataLength;


                    }
                    catch (StringIndexOutOfBoundsException ioe)
                    {
                        RootBranch.Length = "";
                        RootBranch.Value = "";
                        RootBranch.NodeName = "Data Error";
                        RootBranch.Tag = "Data Error";
                    }
                    DefaultMutableTreeNode node = new DefaultMutableTreeNode(RootBranch);
                    Tree.add(node);

                    String parse2Data = RootBranch.Value;
                    //process data block for SPAN ALU
                    if (RootBranch.NodeName.equals("Data"))
                    {
                        if (SpanALU.isSelected())
                        {
                            int StartSpan = 0;
                            for (int k = 0; k < SpanLength.length; k++)
                            {
                                TLV SpanHeader = new TLV();
                                try
                                {
                                    SpanHeader.NodeName = SpanItem[k];
                                    SpanHeader.Value = parse2Data.substring(StartSpan, StartSpan + SpanLength[k]);
                                    StartSpan = StartSpan + SpanLength[k];
                                }
                                catch (StringIndexOutOfBoundsException ioe)
                                {
                                    SpanHeader.Value = "";
                                    SpanHeader.NodeName = "Data Error";
                                    SpanHeader.Tag = "Data Error";
                                }

                                DefaultMutableTreeNode Header = new DefaultMutableTreeNode(SpanHeader);
                                node.add(Header);

                            }
                            // process the table records
                            while (!parse2Data.substring(StartSpan, StartSpan + 10).equals("0000000000"))
                            {
                                TLV SpanRecord = new TLV();
                                try
                                {
                                    SpanRecord.Length = parse2Data.substring(StartSpan + 4, StartSpan + 6);
                                    SpanRecord.NodeName = "Terminal Record, SFI " + parse2Data.substring(StartSpan + 8, StartSpan + 10) + " Record " + parse2Data.substring(StartSpan + 6, StartSpan + 8);
                                    //								System.out.println(SpanRecord.NodeName);
                                    int offset = 2 * Integer.parseInt(parse2Data.substring(StartSpan, StartSpan + 4), 16);
                                    //								System.out.println("offset " + offset);
                                    int dataLength = 2 * Integer.parseInt(SpanRecord.Length, 16);
                                    //								System.out.println("dataLength " + dataLength);
                                    SpanRecord.Value = parse2Data.substring(offset, offset + dataLength);
                                    //								System.out.println("record " + SpanRecord.Value);
                                }
                                catch (StringIndexOutOfBoundsException ioe)
                                {
                                    SpanRecord.Value = "";
                                    SpanRecord.NodeName = "Data Error";
                                    SpanRecord.Tag = "Data Error";
                                }

                                DefaultMutableTreeNode Record = new DefaultMutableTreeNode(SpanRecord);
                                node.add(Record);

                                CurrentNode = Record;
                                String RecordParse = new String(SpanRecord.Value);
                                while (RecordParse.length() != 0)
                                {
                                    RecordParse = Level2Parse(RecordParse);
                                    //								System.out.println("L2 record " + RecordParse);
                                }
                                StartSpan = StartSpan + 10;
                            }
                        }
                        else if (MICAALU.isSelected())
                        {
                            //Data from MACU in integer form
                            String[] MICAName = {"Checksum 1", "Data for checksum 1", "Checksum 7", "Data for checksum 7", "Checksum 8", "Data for checksum 8", "ICC Public Key Length", "Personalisation Data", "Application Life Cycle Data", "ICCDN", "ICCAC", "ICCSMI", "ICCSMC", "Checksum 2", "Data for checksum 2", "Checksum 3", "Data for checksum 3", "Checksum 6", "Data for checksum 6", "Checksum 4", "Data for checksum 4", "Checksum 5", "Data for checksum 5", "Checksum 9", "Data for checksum 9", "PIN Salt", "Reference PIN", "Random Wash", "LCOTA (BCD)", "LCOL (Bin)", "UCOTA (Bin)", "UCOTA (BCD)", "UCOL (Bin)", "LVT TAC", "LVT TAL", "LVT OOL", "LVT OOL", "Currency Conversion", "Application Control", "AIP", "AFL", "ICC Public Key DP/DQ", "ICC Public Key P/Q", "ICC Public Key Modulus", "SFI Table", "Record Table", "Track 2 Equivolent", "Name", "Track 1 Discretionary", "Application Effective Date", "Application Expiry Date", "PAN", "PSN", "SDA Tag List", "CAPKI", "Issuer Public key Cert", "Issuer Public key Exponent", "Issuer Public key Remainder", "SSAD", "ICC Public key Cert", "ICC Public key Exponent", "ICC Public key Remainder"};

                            String[] MICATag = {"DF68", "DF69", "DF74", "DF75", "DF76", "DF77", "DF5F", "DF57", "9F7E", "DF41", "DF42", "DF43", "DF44", "DF6A", "DF6B", "DF6C", "DF6D", "DF72", "DF73", "DF6E", "DF6F", "DF70", "DF71", "DF78", "DF79", "DF59", "DF45", "DF58", "CA", "9F14", "9FA5", "CB", "9F23", "9FA1", "9FA3", "9FA4", "9FA2", "D1", "D5", "82", "94", "DF60", "DF5E", "DF61", "DF5A", "DF5B", "57", "5F20", "9F1F", "5F25", "5F24", "5A", "5F34", "9F4A", "8F", "90", "9F32", "92", "93", "9F46", "9F47", "9F48"};

                            String[] MICALength = {"4", "1F4", "4", "BA", "4", "1C0", "2", "3", "30", "10", "10", "10", "10", "4", "14", "4", "16", "4", "2C", "4", "108", "4", "8", "4", "26", "8", "8", "8", "6", "1", "6", "6", "1", "6", "6", "6", "6", "19", "2", "2", "20", "80", "C0", "80", "3", "8", "15", "1D", "B", "6", "6", "C", "4", "4", "3", "92", "4", "26", "92", "93", "4", "1D"};

                            String[] MICAOffset = {"0", "C", "4", "380", "8", "43A", "10D", "164", "137", "198", "1A8", "1C0", "1D8", "214", "200", "236", "220", "23B", "354", "240", "24C", "244", "2EE", "248", "756", "24C", "254", "25E", "297", "29D", "29E", "2A4", "2AA", "2AB", "2B1", "2B7", "2BD", "2C3", "2F6", "301", "304", "43A", "4BA", "57A", "756", "759", "77F", "794", "7B1", "7D4", "7DA", "7E5", "7F1", "852", "85A", "85D", "8F0", "8F8", "91E", "9B5", "A49", "A4D"};


                            for (int n = 0; n < MICAName.length; n++)
                            {

                                TLV MICAData = new TLV();
                                try
                                {
                                    MICAData.NodeName = MICAName[n];
                                    MICAData.Length = String.valueOf(Integer.parseInt(MICALength[n], 16));
                                    MICAData.Tag = MICATag[n];
                                    MICAData.Value = parse2Data.substring(Integer.parseInt(MICAOffset[n], 16) * 2, Integer.parseInt(MICAOffset[n], 16) * 2 + Integer.parseInt(MICALength[n], 16) * 2);
                                }
                                catch (StringIndexOutOfBoundsException ioe)
                                {
                                    MICAData.Value = "";
                                    MICAData.NodeName = "Data Error";
                                    MICAData.Tag = "Data Error";
                                }

                                DefaultMutableTreeNode Header = new DefaultMutableTreeNode(MICAData);
                                node.add(Header);
                            }


                        }
                    }


                    if (RootBranch.NodeName.equals("Dir") || RootBranch.NodeName.equals("FCI"))
                    {
                        // parse the high level for any lower level values
                        CurrentNode = node;

                        while (RootBranch.encrypted == false && parse2Data.length() != 0)
                        {
                            parse2Data = Level2Parse(parse2Data);
                        }
                    }

                }
                //if data left over
                parseData = parseData.substring(StartChar);
                if (parseData.length() != 0)
                {
                    TLV RootBranch = new TLV();
                    RootBranch.NodeName = "Excess Data";
                    RootBranch.Value = parseData;

                    DefaultMutableTreeNode node = new DefaultMutableTreeNode(RootBranch);
                    Tree.add(node);
                }
            }

            //redraw as tree has changed
            tree.updateUI();
        }

    }

    public String Level2Parse(String toParse)
    {
        int TagEnd = 2;
        int LengthEnd = 2;
        TLV Branch = new TLV();
        // 2 byte tag contains 1F
        if (toParse.substring(1, 2).equals("F") && Integer.parseInt(toParse.substring(0, 1), 16) % 2 == 1)
        {
            TagEnd = 4;
        }
//								System.out.println("Tag length " + TagEnd);

        Branch.Tag = toParse.substring(0, TagEnd);
        Branch.NodeName = Branch.Tag + " ";

        if (TagName.get(Branch.Tag) == null)
        {
            // unidentified tag
            Branch.NodeName = "unknown tag";
            Branch.Value = toParse;
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(Branch);
            sb_details.append("\n    ");
            sb_details.append(Branch.printV());
            CurrentNode.add(node);
            return "";
        }

        // store tag name
        Branch.NodeName = Branch.NodeName + TagName.get(Branch.Tag);


        // 1 byte length top bit = 0
        int dataLength = Integer.parseInt(toParse.substring(TagEnd, TagEnd + 2), 16);
        if (dataLength > 127)
        {
            LengthEnd = 2 + (2 * (dataLength - 128));
            dataLength = Integer.parseInt(toParse.substring(TagEnd + 2, TagEnd + LengthEnd), 16);
        }
        try
        {
            Branch.Length = toParse.substring(TagEnd, TagEnd + LengthEnd);
            Branch.Value = toParse.substring(TagEnd + LengthEnd, TagEnd + LengthEnd + dataLength * 2);
        }
        catch (StringIndexOutOfBoundsException ioe)
        {
            // unidentified tag
            Branch.Length = "Length Error";
            Branch.Value = toParse;
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(Branch);
            CurrentNode.add(node);
            return "";
        }

        DefaultMutableTreeNode node = new DefaultMutableTreeNode(Branch);


        CurrentNode.add(node);
        sb_details.append("\n  ");

        //for composit tags, return all data for furthe parsing
        if (Branch.Tag.equals("70") || Branch.Tag.equals("6F") || Branch.Tag.equals("A5") || Branch.Tag.equals("61"))
        {
            CurrentNode = node;
            sb_details.append(Branch.printTL());
            return toParse.substring(TagEnd + LengthEnd);
        }
        else
        {
            sb_details.append("  ");
            sb_details.append(Branch.printTL());
            sb_details.append(" ");
            sb_details.append(Branch.printV());
            // remove data that has been parsed
            return toParse.substring(TagEnd + LengthEnd + dataLength * 2);
        }
    }

    public String Level1Parse(String data, TLV Found)
    {
        int tagEnd = 4;
        try
        {
            Found.Tag = data.substring(0, 4);
            Found.NodeName = "DGI " + Found.Tag;
            Found.Length = data.substring(tagEnd, tagEnd + 2);
            int dataLength = (tagEnd + 2) + 2 * Integer.parseInt(Found.Length, 16);
            Found.Value = data.substring(tagEnd + 2, dataLength);
            return data.substring(dataLength);
        }
        catch (StringIndexOutOfBoundsException ioe)
        {
            Found.Length = "";
            Found.Value = "";
            Found.NodeName = "Data Error";
            Found.Tag = "Data Error";
            return "";
        }

    }

    private class TLV
    {
        String Tag = "", Length = "", Value = "", NodeName = "Parsed Image";
        boolean encrypted = false;

        public String toString()
        {
            return NodeName;
        }

        public String printTL()
        {
            return Tag + " " + Length;
        }

        public String printV()
        {
            return Value + " [name:" + NodeName + ", isEncrypted:" + encrypted + "]";
        }

        public String printTLV()
        {
            return "\n" + Tag + " " + Length + " " + Value + " [isEncrypted:" + encrypted + "]";
        }
    }


    public void valueChanged(TreeSelectionEvent e)
    {
        // if tree selection changes
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                tree.getLastSelectedPathComponent();

        // if nothing selected
        if (node == null)
        {
            return;
        }

        Object nodeInfo = node.getUserObject();
        TLV dsp = (TLV) nodeInfo;
        if (dsp.Tag.equals("57"))
        {
            //T2 Eq data
            int separator = dsp.Value.indexOf("D");
            viewPane.setText("Tag:\t" + dsp.Tag + "\nLength:\t" + dsp.Length + "\nValue:\t" + dsp.Value
                    + "\n\nPAN:\t" + dsp.Value.substring(0, separator) + "\nExpiry Date:\tYear: " + dsp.Value.substring(separator + 1, separator + 3) + " month: " + dsp.Value.substring(separator + 3, separator + 5) + "\nService Code:\t" + dsp.Value.substring(separator + 5, separator + 8) + "\nPVV:\t" + dsp.Value.substring(separator + 8, separator + 13) + "\niCVV:\t" + dsp.Value.substring(separator + 13, separator + 16));
        }
        else if (dsp.encrypted)
        {
            viewPane.setText("Tag:\t" + dsp.Tag + "\nLength:\t" + dsp.Length + "\nEncrptd Value:\t" + dsp.Value);
        }
        else
        {
            viewPane.setText("Tag:\t" + dsp.Tag + "\nLength:\t" + dsp.Length + "\nValue:\t" + dsp.Value);
        }


    }

    public boolean writeFile(String outputFileDestination, String fileName, String payload, boolean isAppend)
    {
        boolean isErrorWhileWrite = false;

        //String outputFileDestination = fileGroupDestination == null ? outputFilesDirectory : fileGroupDestination;
        //String outputFileDestination = fileGroupDestination;

        FileWriter fileWritter = null;
        BufferedWriter bufferWritter = null;
        try
        {
            File theDir = new File(outputFileDestination);
            // if the directories do not exist, create them
            if (!theDir.exists())
            {
                if (theDir.mkdirs())
                {
                    System.out.println("DIR created : " + outputFileDestination);
                }
            }

            File file = new File(outputFileDestination, fileName.trim());

            // if file doesnt exists, then create it
            if (!file.exists())
            {
                file.createNewFile();
                System.out.println("File created : " + file.getAbsoluteFile());
            }
            fileWritter = new FileWriter(file.getAbsoluteFile(), isAppend);//true);
            bufferWritter = new BufferedWriter(fileWritter);

            bufferWritter.write(payload);
            isErrorWhileWrite = false;
        }
        catch (IOException e)
        {
            isErrorWhileWrite = true;
            System.out.println("writeFile : IOException while writing to the file.." + e);
        }
        finally
        {
            try
            {
                if (bufferWritter != null)
                {
                    bufferWritter.close();
                }
            }
            catch (IOException e)
            {
                System.out.println("writeFile : IOException while closing a BufferedWriter connection.." + e);
            }
            try
            {
                if (fileWritter != null)
                {
                    fileWritter.close();
                }
            }
            catch (IOException e)
            {
                System.out.println("writeFile : IOException while closing a FileWriter connection.." + e);
            }
        }
        return isErrorWhileWrite;

    }


    public static void main(String[] arguments)
    {

        new ImageParser();

    }

}
