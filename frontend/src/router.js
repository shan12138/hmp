import Vue from 'vue'
import Router from 'vue-router'
import AdminLogin from './components/admin/AdminLogin'
import AdminFrame from './components/AdminFrame'
import DistributeRobots from './components/manageRobots/DistributeRobots'
import ManageRobots from './components/manageRobots/ManageRobots'
import RobotHistory from './components/manageRobots/RobotHistory'
import LookUserHistory from './components/userHistory/LookUserHistory'
import SetMedicineRemind from './components/medicineRemind/MedicineRemind'
import LookMedicineHistory from './components/medicineRemind/MedicineHistory'
import ExaminationResults from './components/healthyManage/ExaminationResults'
import VideoMonitor from './components/videoMonitor/VideoMonitor'
import ChangePassword from './components/personalInfo/ChangePassword'
import AlertMonitor from './components/alertMonitor/Alarm'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/admin/login',
      name: 'AdminLogin',
      component: AdminLogin
    },
    {
      path: '/admin',
      name: 'AdminFrame',
      component: AdminFrame,
      children: [
        {
          path: '/admin/DistributeRobots',
          name: 'DistributeRobots',
          component: DistributeRobots
        },
        {
          path: '/admin/ManageRobots',
          name: 'ManageRobots',
          component: ManageRobots
        },
        {
          path: '/admin/RobotHistory',
          name: 'RobotHistory',
          component: RobotHistory
        },
        {
          path: '/admin/LookUserHistory',
          name: 'LookUserHistory',
          component: LookUserHistory
        },
        {
          path: '/admin/SetMedicineRemind',
          name: 'SetMedicineRemind',
          component: SetMedicineRemind
        },
        {
          path: '/admin/LookMedicineHistory',
          name: 'LookMedicineHistory',
          component: LookMedicineHistory
        },
        {
          path: '/admin/ExaminationResults',
          name: 'ExaminationResults',
          component: ExaminationResults
        },
        {
          path: '/admin/VideoMonitor',
          name: 'VideoMonitor',
          component: VideoMonitor
        },
        {
          path: '/admin/ChangePassword',
          name: 'ChangePassword',
          component: ChangePassword
        },
        {
          path: '/admin/Alarm',
          name: 'AlertMonitor',
          component: AlertMonitor
        }
      ]
    }
  ]
})
