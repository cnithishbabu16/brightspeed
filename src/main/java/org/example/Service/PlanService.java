package org.example.Service;

import org.example.DAO.PlanDAO;
import org.example.Model.Plans;
import org.example.Utils.Validations;

import java.sql.Connection;
import java.util.List;

public class PlanService {
    private final PlanDAO planDAO;

    public PlanService(PlanDAO planDAO) {
        this.planDAO = planDAO;
    }

    public List<Plans> getAllPlans(Connection connection) {
        return planDAO.getAllPlans(connection);
    }

    public List<Plans> getUserSubscriptions(Connection connection, String username) {
        return planDAO.getUserSubscriptions(connection, username);
    }

    public boolean subscribeToPlan(Connection connection, String username, int planId) {
        Validations.validatePlanId(planId);
        if(!planDAO.planExists(connection,planId)){
            System.out.println("plan does not exist");
            return false;
        }
        if(planDAO.isUserSubscribed(connection,username,planId)){
            System.out.println("User is already subscribed to the plan");
            return false;
        }
        return planDAO.subscribeToPlan(connection, username, planId);
    }



    public boolean optOutOfPlan(Connection connection, String username, int planId) {
        Validations.validatePlanId(planId);
        return planDAO.optOutOfPlan(connection, username, planId);
    }


}
