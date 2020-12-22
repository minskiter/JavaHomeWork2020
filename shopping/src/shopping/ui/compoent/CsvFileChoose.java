package shopping.ui.compoent;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class CsvFileChoose extends JFileChooser {

    public int result = 0;

    public CsvFileChoose(String path, Component parent, boolean Save){
        super();
        this.setCurrentDirectory(new File(path));
        this.setMultiSelectionEnabled(false);
//        this.addChoosableFileFilter(new FileNameExtensionFilter("csv"));
//        this.setFileFilter(new FileNameExtensionFilter("csv"));
        if (!Save){
            result = this.showOpenDialog(parent);
        }else{
            result = this.showSaveDialog(parent);
        }
    }
}
