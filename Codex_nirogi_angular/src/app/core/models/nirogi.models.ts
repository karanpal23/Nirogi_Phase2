export type UserRole = 'SUPERADMIN' | 'ADMIN' | 'DOCTOR' | 'NODAL_OFFICER';
export type ScreeningCategory = 'CAT1' | 'CAT2' | 'CAT3' | 'CAT4' | 'CAT5' | 'CAT6';
export type ScreeningStatus = 'PENDING' | 'PENDING_LAB' | 'PARTIAL_COMPLETED' | 'COMPLETED' | 'REFERRED';
export type LabTestStatus = 'PENDING' | 'COMPLETED' | 'REJECTED';
export type LabOrderStatus = 'ORDERED' | 'PARTIAL_COMPLETED' | 'COMPLETED' | 'CANCELLED';
export type ReportFormat = 'csv' | 'excel';
export type ReportKind = 'beneficiary' | 'cumulative';

export interface AuthUser {
  username: string;
  role: UserRole;
  firstName: string;
  lastName: string;
  districtId: number | null;
  facilityId: number | null;
}

export interface LoginRequest {
  username: string;
  password: string;
}

export interface MasterDataItem {
  id: number;
  name: string;
}

export interface MasterDataResponse {
  designations: MasterDataItem[];
  facilityTypes: MasterDataItem[];
  districts: MasterDataItem[];
}

export interface Beneficiary {
  memberId: string;
  pppId: string;
  firstName: string;
  lastName: string;
  fatherHusbandFirstname?: string;
  fatherHusbandLastname?: string;
  district: string;
  blockTownCity?: string;
  wardVillage?: string;
  age?: number;
  dateOfBirth: string;
  gender: string;
  mobileNo?: string;
  address?: string;
  lastScreeningDate?: string;
  screeningStatus?: ScreeningStatus;
  category?: ScreeningCategory;
}

export interface PageResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  page: number;
  size: number;
  hasNext: boolean;
  cursor?: string;
}

export interface BeneficiarySearchQuery {
  pppId: string;
  districtId?: number | null;
  facilityId?: number | null;
  page: number;
  size: number;
}

export interface CreateScreeningRequest {
  memberId: string;
  category: ScreeningCategory;
  districtId: number;
  facilityId: number;
  labRequired: boolean;
}

export interface ScreeningResponse {
  referenceId: string;
  status: ScreeningStatus;
  eligibleScheme?: string;
  message: string;
}

export interface SaveScreeningDataRequest {
  referenceId: string;
  historySection: string;
  generalExamSection: string;
  systemicSection: string;
  mandatorySection: string;
  diagnosisSection: string;
  prescriptionSection: string;
}

export interface ScreeningDataView extends SaveScreeningDataRequest {
  category: ScreeningCategory;
  memberId?: string;
  pppId?: string;
  beneficiaryName?: string;
  patientName?: string;
  fullName?: string;
  firstName?: string;
  lastName?: string;
  age?: number;
  dateOfBirth?: string;
  gender?: string;
  districtName?: string;
  facilityName?: string;
}

export interface CreateLabOrderRequest {
  referenceId: string;
  tests: string[];
}

export interface LabResultItem {
  testCode: string;
  resultValue: string;
  remarks?: string;
  normalRange?: string;
}

export interface SubmitLabResultsRequest {
  referenceId: string;
  results: LabResultItem[];
}

export interface LabOrderResponse {
  message: string;
}

export interface LabTestRecord {
  testCode: string;
  testName?: string;
  normalRange?: string;
  status?: LabTestStatus;
}

export interface LabQueueItem {
  referenceId: string;
  memberId: string;
  patientName: string;
  category: ScreeningCategory;
  tests: string[];
  orderedAt: string;
  dueAt: string;
  status: LabOrderStatus;
  overdue: boolean;
}

export interface ReportFilter {
  districtId?: number | null;
  facilityId?: number | null;
  category?: ScreeningCategory | null;
  fromDate?: string | null;
  toDate?: string | null;
  diagnosis?: string | null;
  status?: ScreeningStatus | null;
}

export interface CreateUserRequest {
  role: UserRole;
  firstName: string;
  lastName: string;
  mobile: string;
  email: string;
  designationId: number | null;
  districtId: number | null;
  facilityId: number | null;
  facilityTypeId: number | null;
}

export interface UserResponse extends CreateUserRequest {
  id: number;
  username: string;
  isActive: boolean;
}

export interface DashboardSummary {
  totalScreenings: number;
  pendingLabReports: number;
  completedReports: number;
  referrals: number;
  categoryCounts: Record<ScreeningCategory, number>;
  districtWiseScreenings: Array<{ district: string; count: number }>;
  diseaseStats: Array<{ diagnosis: string; count: number }>;
  doctorProductivity: Array<{ doctorName: string; completed: number; pendingLab: number }>;
  generatedAt: string;
}

export interface NavItem {
  label: string;
  route: string;
  icon: string;
  roles: UserRole[];
}

export interface CategoryFormPayload {
  historySection: Record<string, unknown>;
  generalExamSection: Record<string, unknown>;
  systemicSection: Record<string, unknown>;
  mandatorySection: Record<string, unknown>;
  diagnosisSection: Record<string, unknown>;
  prescriptionSection: Record<string, unknown>;
}

export const SCREENING_CATEGORIES: Array<{ value: ScreeningCategory; label: string; ageGroup: string }> = [
  { value: 'CAT1', label: 'CAT1', ageGroup: '0-6 months' },
  { value: 'CAT2', label: 'CAT2', ageGroup: '6 months-8 years' },
  { value: 'CAT3', label: 'CAT3', ageGroup: '8-18 years' },
  { value: 'CAT4', label: 'CAT4', ageGroup: '18-40 years' },
  { value: 'CAT5', label: 'CAT5', ageGroup: '40-60 years' },
  { value: 'CAT6', label: 'CAT6', ageGroup: 'Above 60 years' },
];

export const USER_ROLES: UserRole[] = ['SUPERADMIN', 'ADMIN', 'DOCTOR', 'NODAL_OFFICER'];

export const COMMON_LAB_TESTS = ['HB', 'RBS', 'TSH', 'CHOLESTEROL', 'CBC', 'LFT', 'KFT', 'URINE_ROUTINE'];
