/*--------------------------------------------------------------------------*/
//																			//
//		Title : XM-Miner 서버 샘플링 모듈									//
//	 	2001년 3월 16일 수정 시작 (by 선지헌)									//
//		2001년 3월 16일 현재 랜덤 샘플링과 클러스터 샘플링 구현되어있음		//
//		Last update : 2001년 3월 21일 선지헌									//
//			- 체계적 표본추출 완료 (3월 21일)									//
//																			//
/*--------------------------------------------------------------------------*/

package xmminer.xmserver.xmgraph.xmdnode.xmdnodesampling;

import java.io.*;
import xmminer.xmlib.xmtable.*;
import xmminer.xmserver.*;
import xmminer.xmserver.xmgraph.xmdnode.xmdatamanage.*;
import java.util.Random;


public class XMBDNodeSampling
{
  private String current_name = "XMDNodeSampling";
  private String project;
  private String p_arc;
  private String n_arc;
  private String job;
  private CXMSampling cs = new CXMSampling();
  private CXMSamplingMetaDataManager cdm;
  private CXMSamplingRunMetaDataReader crr;

  // Modified by Sun Jee Hun --------------------------------------------------//
//  private CXMDataQueryMetaDataManager cdm2;
  // --------------------------------------------------------------------------//

  private int total_rows;
  private int sampling_number;
  private int sampling_index;
  private int cbox_idx;
  private int clustering_number;
  private int m_pbMin;
  private int m_pbMax;
  private int m_pbValue;
  private boolean m_bStop;
  private boolean m_bAlive;
  private Random rand;

  //Modified by Sun Jee Hun ---------------------------------------------------//
  private String[] column_list;
  private String[] column_type;
  //---------------------------------------------------------------------------//

  public XMBDNodeSampling ()
  {
    super();
//	init();
  }

  public int setFileStatus (java.lang.String project_name, 
							java.lang.String previous_arc, 
							java.lang.String next_arc, 
							java.lang.String job_status)
  {
        project = project_name;
        p_arc = previous_arc;
        n_arc = next_arc;
        job = job_status;
        if (job.equals("edit"))
        {
            cdm = new CXMSamplingMetaDataManager();
            int r = cdm.setMetaData(project,p_arc,n_arc,current_name);
            if (r!=0)
            {
                return(1);
            }
            total_rows = cdm.getTotalRows();
			// Modified by Sun Jee Hun ---------------------------------------------//
			// 데이터로부터 컬럼정보를 읽어온다.
			column_list = cdm.getColumnList();
			// ---------------------------------------------------------------------//
            cs.setTotalRows(total_rows);
        }
        else if (job.equals("run"))
        {
            crr = new CXMSamplingRunMetaDataReader();
            int r = crr.setMetaData(project,p_arc,n_arc,current_name);
            if (r!=0)
            {
                return(1);
            }
            total_rows = crr.getTotalRows();
			column_list = crr.getColumnList();

            sampling_number = crr.getSamplingNumber();
            sampling_index = crr.getSelectedIndex();
            cbox_idx = crr.getCboxIndex();
            if (sampling_index==1)
            {
                clustering_number = crr.getClusteringNumber();
            }
            cs.setTotalRows(total_rows);
            cs.setArc(n_arc,p_arc);
            rand = new Random();

        }
        return(0);
  }

  public void close()
  {
  	cdm.close();
  }

  public int calNumToPer (int i_int)
  {
    return cs.calNumToPer(i_int);
  }

// Modified by Sun Jee Hun ---------------------//
// 체계적 표본 추출에서 사용되는 K값의 계산(전체자료 / 샘플수)
  public int calKValue(int sampling_number)
  {
	return cs.calKValue(sampling_number);
  }

  public java.lang.String[] getColumnList()
  {
    return column_list;
  }
// ---------------------------------------------//

  public int calPerToNum (int i_int)
  {
    return cs.calPerToNum(i_int);
  }

  public int getTotalRows ()
  {
    return cs.getTotalRows();
  }

  public void setSamplingNumber (int i_int)
  {
    cdm.setNewRowNum(i_int);
  }

  public void setSelectedIndex (int i_int)
  {
    cdm.setSelectedIndex(i_int);
  }

  public void setCheckBoxValue (int i_int)
  {
    cdm.setCboxIndex(i_int);
  }

  public void setClusteringNumber (int i_int)
  {
    cdm.setClusteringNumber(i_int);
  }

   public int GetPBMin ()
  {
    SetPBMin();
    return m_pbMin;
  }

  private void SetPBMin ()
  {
    m_pbMin = 1;
  }

  public int GetPBMax ()
  {
    SetPBMax();
    return m_pbMax;
  }

  private void SetPBMax ()
  {
    if (cbox_idx==0)		// 비복원추출
    {
       m_pbMax = sampling_number+total_rows;
    }
    else if (cbox_idx==1)	// 복원 추출
    {
    	m_pbMax = sampling_number;
    }
  }

  public int GetPBValue ()
  {
    return m_pbValue;
  }

  // Modified by Sun Jee Hun-----------------------------//
  // 실제 행동하는 부분
  public int PerformRunning()
  {
	  cs.setPreSamplingStatus(project, cbox_idx);
	  switch (sampling_index)
	  {
		case 0:
			return randomSampling();

		case 1:
			return clusterSampling();

		case 2:
			return systematicSampling();

		default:
			return (0);
	  }
  }
  // ----------------------------------------------------//

  // Modified by Sun Jee Hun-----------------------------//
  // 체계적 표본추출
  private int systematicSampling()
  {
	int first_number = 0;
	int next_number = 0;
	int K = 0;

	K = total_rows / sampling_number;
	first_number = rand.nextInt(K) + 1;
	next_number = first_number + K;

	cs.setNumberIndex(first_number);
	m_pbValue = 1;

	for (int x = 2; KeepRunning() && x <= m_pbMax; )
	{
		m_pbValue = x;
		cs.setNumberIndex(next_number);
		next_number = next_number + K;
		x++;
	}
	cs.setPostSamplingStatus(cbox_idx);
	return(0);
  }
  // ----------------------------------------------------//


  // 랜덤 샘플링
  private int randomSampling()
  {
      int rand_number = 0;
      if (cbox_idx==1)	// 복원 추출
      {
		 for (int x = m_pbMin; KeepRunning() && x <= m_pbMax; )
         {
       	m_pbValue = x;
             rand_number = rand.nextInt(total_rows)+1;
             cs.setNumberIndex(rand_number);
             x++;
         }
      }
      else if (cbox_idx==0)	// 비복원 추출
      {
         for (int x = m_pbMin; KeepRunning() && x <= m_pbMax;)
         {
       	      m_pbValue = x;
       	      if (x<total_rows)
       	      {
       	          cs.indexDataCreate(x);
       	          x++;
       	      }
       	      else if (x==total_rows)
       	      {
       	          cs.indexDataCreate(x);
       	          cs.setIndexFile();
       	          x++;
       	      }
       	      else
       	      {
                        rand_number = rand.nextInt(total_rows)+1;
                        if (!cs.dataFind(rand_number))
                        {
                              cs.setNumberIndex(rand_number);
                              x++;
                        }
                  }
         }
     }
    cs.setPostSamplingStatus(cbox_idx);
    return(0);
  }

  // 군집 샘플링
  private int clusterSampling()
  {
      int rand_number = 0;
      int cluster_cnt = 1;
      int number_cnt = 0;
      int number_in_cluster = getNumberInCluster();
      int[] cluster_sampling_number = getSamplingNumberInCluster();

      if (cbox_idx==1)	// 복원 추출
      {
		 for (int x = m_pbMin; KeepRunning() && x <= m_pbMax; )
         {
			 m_pbValue = x;

             if (number_cnt == cluster_sampling_number[cluster_cnt-1])
             {
				   cluster_cnt = cluster_cnt + 1;
                   number_cnt = 0;
             }else{}

             if (cluster_cnt==clustering_number)
             {
					rand_number = getRandNumber(number_in_cluster,cluster_cnt,true);
                    cluster_cnt = 1;
             }

             else
             {
                    rand_number = getRandNumber(number_in_cluster,cluster_cnt,false);
             }
             cs.setNumberIndex(rand_number);
             number_cnt = number_cnt + 1;
             x++;
         }
      }
      else if (cbox_idx==0)	// 비복원추출
      {
            for (int x = m_pbMin; KeepRunning() && x <= m_pbMax;)
            {
       	      m_pbValue = x;
       	      if (x<total_rows)
       	      {
       	          cs.indexDataCreate(x);
       	          x++;
       	      }
       	      else if (x==total_rows)
       	      {
       	          cs.indexDataCreate(x);
       	          cs.setIndexFile();
       	          x++;
       	      }
       	      else
       	      {
                      if (number_cnt == cluster_sampling_number[cluster_cnt-1])
                      {
                            cluster_cnt = cluster_cnt + 1;
                            number_cnt = 0;
                      }
                      if (cluster_cnt==clustering_number)
                      {
                           rand_number = getRandNumber(number_in_cluster,cluster_cnt,true);
                      }
                      else
                      {
                           rand_number = getRandNumber(number_in_cluster,cluster_cnt,false);
                      }
                      if (!cs.dataFind(rand_number))
                      {
                            cs.setNumberIndex(rand_number);
                            number_cnt = number_cnt + 1;
                            x++;
                      }
                   }
            }
      }
    cs.setPostSamplingStatus(cbox_idx);
    return(0);
  }

  private int getNumberInCluster()
  {
      int quot = total_rows/clustering_number;
      return quot;
  }

  private int[] getSamplingNumberInCluster()
  {
      int[] result = new int[clustering_number];
      int quot = sampling_number/clustering_number;
      int mod = sampling_number%clustering_number;
      for (int i=0; i<clustering_number; i++)
      {
          if (mod>0)
          {
              result[i] = quot + 1;
              mod = mod - 1;
          }
          else
          {
              result[i] = quot;
          }
      }
      return result;
  }

  private int getModNumber()
  {
      int mod = total_rows%clustering_number;
      return mod;
  }

  private int getRandNumber(int number_in_cluster, int cluster_cnt, boolean last_cluster_check)
  {
        if (last_cluster_check)
        {
            return getNumberInLastCluster(number_in_cluster, cluster_cnt);
        }
        else
        {
            return getNumberInOtherCluster(number_in_cluster, cluster_cnt);
        }
  }

  private int getNumberInLastCluster(int number_in_cluster, int cluster_cnt)
  {
        int rand_number = rand.nextInt(total_rows)+1;
        if (cluster_cnt==1)
        {
              return rand_number;
        }
        else
        {
              int init_limit_number = number_in_cluster*(cluster_cnt-1);
              if (init_limit_number < rand_number)
              {
                  return rand_number;
              }
              else
              {
                  return getNumberInLastCluster(number_in_cluster, cluster_cnt);
              }
        }
  }

  private int getNumberInOtherCluster(int number_in_cluster, int cluster_cnt)
  {
        int limit_number = number_in_cluster*cluster_cnt;
        int rand_number = rand.nextInt(limit_number)+1;
        if (cluster_cnt==1)
        {
              if (rand_number<=limit_number)
              {
                  return rand_number;
              }
              else
              {
                  return getNumberInOtherCluster(number_in_cluster, cluster_cnt);
              }
        }
        else
        {
              int init_limit_number = number_in_cluster*(cluster_cnt-1);
              if ((init_limit_number < rand_number)&&(rand_number<= limit_number))
              {
                  return rand_number;
              }
              else
              {
                  return getNumberInOtherCluster(number_in_cluster, cluster_cnt);
              }
        }
  }

  public int Run ()
  {
    m_bStop = false;
    m_bAlive = true;
    int r = PerformRunning();
    m_bAlive = false;
    m_bStop = true;
    return(r);
  }

  public boolean KeepRunning ()
  {
    return(!m_bStop);
  }

  public void StopRunning ()
  {
    m_bStop = true;
  }

  public boolean IsRunning ()
  {
    return(m_bAlive);
  }

//  public static ServerMonitorPage monitor = null;
//  private void init() {
//    java.util.ResourceBundle res = java.util.ResourceBundle.getBundle("xmminer.xmserver.ServerResources");
//    String name = "XMBDNodeSampling";
//    if (monitor == null) {
////    monitor = ServerMonitor.addPage(this, name);
////    monitor.showObjectCounter(true);
////	  monitor.showReadCounter(true);
////	  monitor.showWriteCounter(true);
//      ServerMonitor.register(name);
//    }
////    monitor.updateObjectCounter(1);
//    ServerMonitor.log(ServerResources.format(res.getString("created"), name));
//  }
}