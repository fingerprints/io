/*
 * XLSFileWriter.java
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

import java.io.File;
import java.io.IOException;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * @author fuzail_s
 * 
 */
public class XLSFileWriter {

    public static void write(String path, List<List<String>> rows) {
        WritableWorkbook workbook = null;
        try {
            File file = new File(path);
            String sheetName = path.substring(
                    path.lastIndexOf(File.separatorChar) + 1,
                    path.lastIndexOf("."));

            workbook = Workbook.createWorkbook(file);
            WritableSheet sheet = workbook.createSheet(sheetName, 0);

            int rowNumber = 0;
            for (List<String> cells : rows) {
                int cellNumber = 0;
                for (String cell : cells) {
                    Label label = new Label(cellNumber, rowNumber, cell);
                    sheet.addCell(label);
                    cellNumber++;
                }
                rowNumber++;
            }

            workbook.write();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RowsExceededException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (WriteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (workbook != null) {
                    workbook.close();
                }
            } catch (WriteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

}
