import { NavItem } from '../models/nirogi.models';

export const NAV_ITEMS: NavItem[] = [
  {
    label: 'Dashboard',
    route: '/dashboard',
    icon: 'D',
    roles: ['SUPERADMIN', 'ADMIN', 'DOCTOR', 'NODAL_OFFICER'],
  },
  {
    label: 'Beneficiaries',
    route: '/beneficiaries',
    icon: 'B',
    roles: ['SUPERADMIN', 'ADMIN', 'DOCTOR', 'NODAL_OFFICER'],
  },
  {
    label: 'Screening',
    route: '/screening/new',
    icon: 'S',
    roles: ['DOCTOR', 'SUPERADMIN'],
  },
  {
    label: 'Screening Data',
    route: '/screening-data',
    icon: 'SD',
    roles: ['DOCTOR', 'SUPERADMIN'],
  },
  {
    label: 'Lab Workflow',
    route: '/lab',
    icon: 'L',
    roles: ['DOCTOR', 'SUPERADMIN', 'ADMIN', 'NODAL_OFFICER'],
  },
  {
    label: 'Reports',
    route: '/reports',
    icon: 'R',
    roles: ['SUPERADMIN', 'ADMIN', 'DOCTOR', 'NODAL_OFFICER'],
  },
  {
    label: 'Users',
    route: '/users',
    icon: 'U',
    roles: ['SUPERADMIN', 'ADMIN'],
  },
  {
    label: 'Facilities',
    route: '/facilities',
    icon: 'F',
    roles: ['SUPERADMIN', 'ADMIN'],
  },
  {
    label: 'Master Data',
    route: '/master-data',
    icon: 'M',
    roles: ['SUPERADMIN', 'ADMIN'],
  },
];
