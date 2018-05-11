import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URISyntaxException;

class UI {

    private String firstImagePathValue;
    private String secondImagePathValue;

    UI() {
        final JFrame frame = new JFrame("Лабораторный практикум ОИз");
        System.setProperty("apple.awt.fileDialogForDirectories", "true");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(1000, 800);
        final FlowLayout layout = new FlowLayout(FlowLayout.TRAILING);
        frame.getContentPane().setLayout(layout);


        JPanel outputPanel = new JPanel();
        outputPanel.setBounds(0, 0, 500,400);
        final JLabel imgLabel = new JLabel(new ImageIcon());
        outputPanel.add(imgLabel);
        final JLabel imgLabelTwo = new JLabel(new ImageIcon());
        outputPanel.add(imgLabelTwo);
        frame.getContentPane().add(outputPanel);


        JPanel firstImagePanel = new JPanel();
        firstImagePanel.setBounds(500,0,500,400);
        final JLabel firstImage = new JLabel(new ImageIcon());
        firstImagePanel.add(firstImage);
        JButton pickImage = new JButton("Выбрать изображение №1");
        pickImage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                firstImagePathValue = getImagePath();
                if (firstImagePathValue != null) {
                    firstImage.setIcon(ImageRescaler.rescaleImage(new File(firstImagePathValue), 400, 500));
                    frame.pack();
                }
            }
        });
        firstImagePanel.add(pickImage);
        frame.getContentPane().add(firstImagePanel);

        JPanel secondImagePanel = new JPanel();
        secondImagePanel.setBounds(0,400,500,400);
        final JLabel secondImage = new JLabel(new ImageIcon());
        secondImagePanel.add(secondImage);
        JButton pickImageTwo = new JButton("Выбрать изображение №2");
        pickImageTwo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                secondImagePathValue = getImagePath();
                if (secondImagePathValue != null) {
                    secondImage.setIcon(ImageRescaler.rescaleImage(new File(secondImagePathValue), 400, 500));
                    frame.pack();
                }
            }
        });
        secondImagePanel.add(pickImageTwo);
        frame.getContentPane().add(secondImagePanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4,1));
        buttonPanel.setBounds(500,400,500,400);
        JButton stereoscopic = new JButton("Стерескопическое изображение");
        stereoscopic.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (firstImagePathValue != null && secondImagePathValue != null) {
                    try {
                        StereoscopicImage str = new StereoscopicImage(firstImagePathValue, secondImagePathValue);
                        imgLabel.setIcon(ImageRescaler.rescaleImage(str.result, 400, 500));
                        frame.pack();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (URISyntaxException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        buttonPanel.add(stereoscopic);
        JButton medianFilter = new JButton("Медианный фильтр");
        medianFilter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (firstImagePathValue != null) {
                        MedianFilter filter = new MedianFilter(new File(firstImagePathValue));
                        imgLabel.setIcon(ImageRescaler.rescaleImage(filter.result,400, 500));
                        frame.pack();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        buttonPanel.add(medianFilter);
        JButton histogramFilter = new JButton("Гистограммный фильтр");
        histogramFilter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (firstImagePathValue != null) {
                        HistogramFilter filter = new HistogramFilter(new File(firstImagePathValue));
                        imgLabel.setIcon(ImageRescaler.rescaleImage(filter.result,400, 500));
                        frame.pack();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        buttonPanel.add(histogramFilter);
        JButton brightness = new JButton("Выравнивание яркости");
        brightness.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (firstImagePathValue != null && secondImagePathValue != null) {
                        Brightness brightness = new Brightness(firstImagePathValue, secondImagePathValue);
                        imgLabel.setIcon(ImageRescaler.rescaleImage(brightness.resultFirst,400, 500));
                        imgLabelTwo.setIcon(ImageRescaler.rescaleImage(brightness.resultSecond,400, 500));
                        frame.pack();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        buttonPanel.add(brightness);

        buttonPanel.setBackground(Color.BLUE);
        frame.getContentPane().add(buttonPanel);
        JTextArea furieInput = new JTextArea();
        furieInput.setSize(new Dimension(400, 300));
        furieInput.setText("Input:\n{1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0}\n");
        JTextArea furieOutput = new JTextArea();
        furieOutput.setSize(new Dimension(400, 300));
        JButton furieButton = new JButton("Преобразование фурье");
        furieButton.addActionListener((e) -> {
            furieOutput.setText(new FastFourierTransform().preform());
        });
        buttonPanel.add(furieButton);

        frame.add(furieInput, BorderLayout.NORTH);
        frame.add(furieOutput, BorderLayout.SOUTH);
        frame.setFocusable(true);
        frame.setVisible(true);
    }

    private String getImagePath() {
        String userDir = System.getProperty("user.home");
        JFileChooser fileOpen = new JFileChooser(userDir + "/Desktop/images");
        fileOpen.setFileFilter(new ImageFilter());
        int ret = fileOpen.showDialog(null, "Открыть файл");
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fileOpen.getSelectedFile();
            return file.getAbsolutePath();
        }
        return null;
    }

    class ImageFilter extends javax.swing.filechooser.FileFilter implements FileFilter {

        @Override
        public boolean accept(File pathname) {
            String filename = pathname.getName();
            return pathname.isDirectory() || filename.endsWith("jpg'") || filename.endsWith("jpeg") ||
                    filename.endsWith("png") || filename.endsWith("gif");
        }

        @Override
        public String getDescription() {
            return "Images";
        }
    }


}
