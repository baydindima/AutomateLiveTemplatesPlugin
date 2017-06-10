package edu.jetbrains.plugin.lt.newui;

import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.ui.EditorTextField;
import com.intellij.ui.components.JBPanel;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import scala.edu.jetbrains.plugin.lt.finder.common.Template;
import edu.jetbrains.plugin.lt.util.TemplateOps;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ChooseTemplate extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JPanel bodyPane;
    private JButton prevButton;
    private JButton nextButton;
    private JLabel fileTypeLabel;
    private JLabel occurenceCountLabel;
    private JButton addToTemplatesButton;
    private JLabel errorLabel;
    private JLabel curPosLabel;
    private EditorTextField textField;

    private final Project project;
    private final FileType fileType;

    private List<Template> templateList;
    private int curIndex;

    public ChooseTemplate(Project project, FileType fileType, List<Template> templateList) {
        this.project = project;
        this.fileType = fileType;
        this.templateList = templateList;

        $$$setupUI$$$();
        setContentPane(contentPane);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        fileTypeLabel.setText("File type: " + fileType.getName());

        bodyPane.add(textField, BorderLayout.CENTER);
        buttonOK.addActionListener(e -> onOk());
        addToTemplatesButton.addActionListener(e -> saveTemplate());
        setNewTemplate(curIndex);
    }

    public void showDialog() {
        pack();
        setVisible(true);
    }

    private void onOk() {
        dispose();
    }

    private void nextTemplate() {
        setNewTemplate(++curIndex);
    }

    private void prevTemplate() {
        setNewTemplate(--curIndex);
    }

    private void setNewTemplate(int templateIndex) {
        if (templateIndex < 0 || templateIndex >= templateList.size())
            throw new NoSuchTemplateException("No such template index : " + templateIndex + " from " + templateList.size());

        nextButton.setEnabled(templateIndex < templateList.size() - 1);
        prevButton.setEnabled(templateIndex > 0);

        Template template = templateList.get(templateIndex);

        textField.setText(template.text());
        occurenceCountLabel.setText("Occurrence count: " + template.templateStatistic().occurrenceCount());
        curPosLabel.setText(String.format("%d/%d", curIndex + 1, templateList.size()));
    }

    private void createUIComponents() {
        nextButton = new JButton(new ImageIcon(getClass().getClassLoader().getResource("png/arrow_right.png")));
        nextButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (nextButton.isEnabled()) {
                    nextTemplate();
                }
                super.mouseClicked(e);
            }
        });
        prevButton = new JButton(new ImageIcon(getClass().getClassLoader().getResource("png/arrow_left.png")));
        prevButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (prevButton.isEnabled()) {
                    prevTemplate();
                }
                super.mouseClicked(e);
            }
        });
        bodyPane = new JBPanel<>(new BorderLayout());
        textField = new EditorTextField(EditorFactory.getInstance().createDocument(""), project, fileType, true, false);
    }

    private void saveTemplate() {
        Template template = templateList.get(curIndex);
        String abbreviation = new ChooseTemplateAbbreviation().showDialog();
        if (abbreviation.isEmpty()) {
            errorLabel.setText("Empty abbreviation");
            return;
        }
        if (!TemplateOps.isPossibleAbbreviation(abbreviation)) {
            errorLabel.setText("Used abbreviation");
            return;
        }
        TemplateOps.saveTemplate(template, abbreviation, "");
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(3, 4, new Insets(10, 10, 10, 10), -1, -1));
        panel1.add(contentPane, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel2, new GridConstraints(2, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel2.add(spacer1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(panel3, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonOK = new JButton();
        buttonOK.setText("OK");
        panel3.add(buttonOK, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        errorLabel = new JLabel();
        errorLabel.setText("");
        panel2.add(errorLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        contentPane.add(bodyPane, new GridConstraints(1, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(600, 400), null, null, 0, false));
        prevButton.setText("");
        contentPane.add(prevButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nextButton.setText("");
        contentPane.add(nextButton, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        fileTypeLabel = new JLabel();
        fileTypeLabel.setText("Label");
        contentPane.add(fileTypeLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        occurenceCountLabel = new JLabel();
        occurenceCountLabel.setText("Label");
        contentPane.add(occurenceCountLabel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addToTemplatesButton = new JButton();
        addToTemplatesButton.setText("Add to templates");
        contentPane.add(addToTemplatesButton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        curPosLabel = new JLabel();
        curPosLabel.setText("Label");
        contentPane.add(curPosLabel, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }
}