/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.prs.controller;

import uit.prs.arnetminer.AMinerDataSet;

/**
 *
 * @author Huynh Ngoc Tin
 */
public class MainController {
    public static void main(String args[]) {
        AMinerDataSet aMiner = new AMinerDataSet();
        //AMinerDataSet.portAMinerDataToPRSData("D:\\AMiner-Author.txt.001", "filePaper_AMiner", "fileCoAuthor_AMiner", "D:\\PRS-Author.txt");
        aMiner.readFile_AMinerAuthor("D:\\AMiner-Author.txt.001");
        aMiner.readFile_AMinerPaper("D:\\AMiner-Paper.txt.001");
        aMiner.readFile_AMinerCoAuthor("D:\\AMiner-Coauthor.txt.001");
        
        System.out.println(".....");
        
    }
}
