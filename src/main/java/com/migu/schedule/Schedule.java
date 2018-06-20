package com.migu.schedule;


import com.migu.schedule.constants.ReturnCodeKeys;
import com.migu.schedule.info.Node;
import com.migu.schedule.info.Task;
import com.migu.schedule.info.TaskInfo;

import java.util.ArrayList;
import java.util.List;

/*
*类名和方法不能修改
 */
public class Schedule {

	private List<Node> nodeList = new ArrayList<Node>();
    private List<Task> hangTask = new ArrayList<Task>();

    public int init() {
    	hangTask.clear();
    	nodeList.clear();
        return ReturnCodeKeys.E001;
    }


    public int registerNode(int nodeId) {
    	 if(nodeId <= 0) {
             return ReturnCodeKeys.E004;
         }
         for(Node node : nodeList) {
             if(nodeId == node.getNodeId()) {
                 return ReturnCodeKeys.E005;
             }
         }
         Node newNode = new Node();
         newNode.setNodeId(nodeId);
         nodeList.add(newNode);
         return ReturnCodeKeys.E003;
    }

    public int unregisterNode(int nodeId) {
    	if(nodeId <= 0) {
            return ReturnCodeKeys.E004;
        }
        for (int i = 0; i < nodeList.size(); i++) {
        	 if(nodeList.get(i).getNodeId() == nodeId) {
        		 hangTask.addAll(nodeList.get(i).getTaskList());
                 nodeList.remove(i);
                 return ReturnCodeKeys.E006;
             }
		}
        return ReturnCodeKeys.E007;
    }


    public int addTask(int taskId, int consumption) {
    	 if(taskId <= 0) {
             return ReturnCodeKeys.E009;
         }
         for(Task task : hangTask) {
             if(task.getTaskId() == taskId) {               
                 return ReturnCodeKeys.E010;
             }
         }
         for(Node node : nodeList) {
             List<Task> task = node.getTaskList();
             for(Task existsTask : task) {
                 if(existsTask.getTaskId() == taskId) {
                     return ReturnCodeKeys.E010;
                 }
             }
         }

         Task task = new Task();
         task.setTaskId(taskId);
         task.setConsumption(consumption);
         hangTask.add(task);
         return ReturnCodeKeys.E008;
    }


    public int deleteTask(int taskId) {
    	if(taskId <= 0) {
            return ReturnCodeKeys.E009;
        }
        for(int i=0; i<hangTask.size(); i++) {
            if(hangTask.get(i).getTaskId() == taskId) {
                hangTask.remove(i);
                return ReturnCodeKeys.E011;
            }
        }
        for(int i=0; i<nodeList.size(); i++) {
            List<Task> tasks = nodeList.get(i).getTaskList();
            for(Task existsTask : tasks) {
                if(existsTask.getTaskId() == taskId) {
                    nodeList.get(i).removeTask(taskId);
                    return ReturnCodeKeys.E011;
                }
            }
        }
        return ReturnCodeKeys.E012;
    }


    public int scheduleTask(int threshold) {
    	if(threshold <= 0) {       
            return ReturnCodeKeys.E002;
        }
        return ReturnCodeKeys.E000;
    }


    public int queryTaskStatus(List<TaskInfo> tasks) {
         return ReturnCodeKeys.E000;
    }

}
