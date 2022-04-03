package ru.liga.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import ru.liga.domain.Profile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@RequiredArgsConstructor
@Service
public class ImageService {

    @Value("${upload.path}")
    private String uploadPath;

    public File getFile(Profile profile) {
        File result = null;
        try {
            File backgroundImage = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + uploadPath);
            BufferedImage image = ImageIO.read(backgroundImage);
            Font header = new Font("Old Standard TT", Font.BOLD, 44);
            Font body = new Font("Old Standard TT", Font.PLAIN, 30);
            Graphics g = image.getGraphics();
            g.setColor(Color.BLACK);

/*        g.setFont(body);
        g.drawString(translator.translate(userService.findAllWeLike().contains(user) ? "Любо": "Нэ любо"), 60, 50);*/

            g.setFont(header);
            g.drawString(profile.getName(), 60, 130);

            g.setFont(body);
            FontMetrics fm = g.getFontMetrics(body);
            int lineHeight = fm.getHeight();
            String textToDraw = profile.getDescription();
            String[] arr = textToDraw.split(" ");
            int nIndex = 0;
            int startX = 60;
            int startY = 190;
            while (nIndex < arr.length) {
                String line = arr[nIndex++];
                while ((nIndex < arr.length) && (fm.stringWidth(line + " " + arr[nIndex]) < 447)) {
                    line = line + " " + arr[nIndex];
                    nIndex++;
                }
                g.drawString(line, startX, startY);
                startY = startY + lineHeight;
            }
            result = new File(backgroundImage.getParentFile(), "result_image.jpg");
            ImageIO.write(image, "jpg", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
