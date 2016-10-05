//Copyright:    Copyright (c) 1999
//Author:       최대우
//Company:      전북대학교 산업공학과 지능정보시스템
//Description:  Your description
package xmminer.xmserver.xmgraph.xmmnode.xmmnodec45.rulegen.typedef;



public class XMCCondition
{
    public XMCTest		CondTest;	//test part of condition
    public int        TestValue;	// specified outcome of test
    
    public    XMCCondition()
    {
    	   CondTest = new   XMCTest();
    }
}