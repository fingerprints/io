/*
 * DelimitedTextReader.java
 *
 * Copyright (C) 2010 Fingerprints Software
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with this library.
 * If not, see <http://www.gnu.org/licenses/>.
 */
package org.fingerprintsoft.io;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fuzail_s
 * 
 */
public class DelimitedTextFileReader {
    
    public static final DelimitedTextFileReader COMMA_SEPERATED_VALUE = new DelimitedTextFileReader(
            String.valueOf(","));
    public static final DelimitedTextFileReader TAB_SEPERATED_VALUE = new DelimitedTextFileReader(
            String.valueOf("\t"));
    public static final DelimitedTextFileReader PIPE_SEPERATED_VALUE = new DelimitedTextFileReader(
            String.valueOf("\\|"));
    public static final DelimitedTextFileReader SPACE_SEPERATED_VALUE = new DelimitedTextFileReader(
            String.valueOf(" "));


    private String delimeter;

    public DelimitedTextFileReader(String delimeter) {
        this.delimeter = delimeter;
    }

    public List<List<String>> read(String path) throws FileNotFoundException,
            IOException {
        List<List<String>> rows = null;
        List<String> cells = null;
        String rowText = null;
        FileInputStream fileInputStream = null;
        DataInputStream dataInputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileInputStream = new FileInputStream(path);

            dataInputStream = new DataInputStream(fileInputStream);

            inputStreamReader = new InputStreamReader(dataInputStream);

            bufferedReader = new BufferedReader(inputStreamReader);

            int i = 0;
            rows = new ArrayList<List<String>>();
            while ((rowText = bufferedReader.readLine()) != null) {
                String[] cell = rowText.split(getDelimeter());
                cells = new ArrayList<String>(cell.length);
                for (int j = 0; j < cell.length; j++) {
                    cells.add(cell[j]);
                }
                rows.add(cells);
                i++;
            }
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();

                }
                if (dataInputStream != null) {
                    dataInputStream.close();
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return rows;
    }

    public String getDelimeter() {
        return delimeter;
    }

    public void setDelimeter(String delimeter) {
        this.delimeter = delimeter;
    }

}
