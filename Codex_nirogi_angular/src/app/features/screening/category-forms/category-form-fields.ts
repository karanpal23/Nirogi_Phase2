import { FormControl, FormGroup } from '@angular/forms';

import { CategoryFormPayload, ScreeningCategory, ScreeningDataView } from '../../../core/models/nirogi.models';

export interface CategoryFormSection {
  sectionName: string;
  fields: string[];
}

export interface CategoryFormDefinition {
  category: ScreeningCategory;
  sections: CategoryFormSection[];
}

type PayloadBucket = keyof CategoryFormPayload;

export const CATEGORY_FORM_DEFINITIONS: Record<ScreeningCategory, CategoryFormDefinition> = {
  CAT1: {
    category: 'CAT1',
    sections: [
      {
        sectionName: 'History / Symptoms / Complaints',
        fields: [
          'Mode of delivery',
          'Cry after birth',
          'Any History of previous Admission in hospital or physical illness',
          'Any Significant family History',
          'Family History If Yes',
          'Immunization Status',
          'History of Feeding',
          'History of contact with TB patient',
        ],
      },
      { sectionName: 'Milestones', fields: ['Neck Holding', 'Social smile', 'Sitting with support'] },
      {
        sectionName: 'General Physical Examination',
        fields: [
          'Weight (in kg)',
          'Length (in cm)',
          'Head circumference (in cm)',
          'Pallor',
          'Jaundice',
          'Cyanosis',
          'Heart rate (per min)',
          'Respiratory rate (per min)',
          'Capillary Refill Time (< 3 sec)',
          'Oxygen Saturation (> 93%)',
          'Congenital anomalies',
          'Anterior fontanelle',
          'Any Skin Lesions',
        ],
      },
      {
        sectionName: 'Systemic Examination',
        fields: [
          'Chest',
          'CVS',
          'Per abdomen',
          'CNS',
          'Hearing',
          'Eye Congenital cataract',
          'Movement of eye ball with light',
        ],
      },
      {
        sectionName: 'Mandatory Investigations',
        fields: ['Hb (in g/dl)', 'Relevant investigation if advised by specialist'],
      },
      {
        sectionName: 'Diagnosis',
        fields: [
          'Diagnosis',
          'Already Known',
          'Other Disease / Diagnosis Notes',
          'National Health Programs',
          'Reason For Refer',
        ],
      },
      {
        sectionName: 'Reason For Refer Options',
        fields: ['For detailed evaluation', 'For treatment', 'Specialist consultation'],
      },
      { sectionName: 'Prescription', fields: ['Prescription (if any)'] },
    ],
  },
  CAT2: {
    category: 'CAT2',
    sections: [
      {
        sectionName: 'History / Symptoms / Complaints',
        fields: [
          'Mode of delivery',
          'Cry after birth',
          'Any History of previous Admission in hospital or physical illness',
          'Any Significant family History',
          'Immunization Status',
          'History of Feeding',
          'History of contact with TB patient',
          'History of Deworming',
        ],
      },
      {
        sectionName: 'Milestones - 6 Months to 1 Year',
        fields: [
          'Sitting without Support',
          'Standing without Support',
          'Standing with Support',
          'Pincer grasp',
          'Stranger anxiety',
          'Bisyllable speech like Ba, Ma, Pa',
        ],
      },
      {
        sectionName: 'Milestones - 1 to 2 Years',
        fields: ['Walking without Support', 'Speak 5 to 10 words', 'Follow simple directions'],
      },
      {
        sectionName: 'Milestones - 2 to 5 Years',
        fields: ['Walk up & down stairs with Support', 'Jump with both feet', 'Scrabbling', 'Speak at least 50 words'],
      },
      {
        sectionName: 'General Physical Examination',
        fields: [
          'Weight (in kg)',
          'Length/Height (in cm)',
          'Head circumference (in cm)',
          'Mid-arm circumference (in cm)',
          'Pallor',
          'Jaundice',
          'Cyanosis',
          'Heart rate / Pulse rate (per min)',
          'Respiratory rate (per min)',
          'Pedal oedema',
          'Oxygen Saturation',
          'Congenital anomalies',
          'Anterior fontanelle',
          'Hair texture',
          'Skin texture',
          'Skin turgor',
          'Any Skin Lesions',
        ],
      },
      { sectionName: 'Systemic Examination', fields: ['Chest', 'CVS', 'Per abdomen', 'CNS', 'Hearing'] },
      { sectionName: 'Eye Refraction', fields: ['Right eye', 'Left eye', 'Colour blindness'] },
      { sectionName: 'Dental Examination', fields: ['Dental examination'] },
      {
        sectionName: 'Mandatory Investigations',
        fields: ['Hb (in g/dl)', 'Relevant investigation if advised by specialist'],
      },
      {
        sectionName: 'Diagnosis',
        fields: [
          'Diagnosis',
          'Already Known',
          'Other Disease / Diagnosis Notes',
          'National Health Programs',
          'Reason For Refer',
        ],
      },
      { sectionName: 'Prescription', fields: ['Prescription (if any)'] },
    ],
  },
  CAT3: {
    category: 'CAT3',
    sections: [
      {
        sectionName: 'History / Symptoms / Complaints',
        fields: [
          'Any history of previous admission in hospital or physical illness',
          'Any significant family history',
          'Immunization status',
          'History of contact with TB patient',
          'History of Deworming',
        ],
      },
      {
        sectionName: 'General Physical Examination',
        fields: [
          'Weight (in kg)',
          'Height (in cm)',
          'BMI (kg/m²)',
          'Pallor',
          'Jaundice',
          'Cyanosis',
          'Pulse rate (per min)',
          'Respiratory rate (per min)',
          'Blood pressure (mmHg)',
          'Pedal oedema',
          'Congenital anomalies',
          'Skin turgor',
          'Any Skin Lesions',
          'Wrist widening',
          'Sign of Vitamin-A deficiency',
          'Gonadal examination',
          'History of ear discharge',
          'Speech',
          'IQ',
        ],
      },
      {
        sectionName: 'Systemic Examination',
        fields: ['Chest', 'CVS', 'Per abdomen', 'CNS', 'Hearing', 'Genital examination', 'Dental examination'],
      },
      { sectionName: 'Eye Refraction', fields: ['Right eye', 'Left eye', 'Colour blindness'] },
      {
        sectionName: 'Mandatory Investigations',
        fields: [
          'Hb (in g/dl)',
          'TLC (/cumm)',
          'Packed Cell Volume (in L/L)',
          'Mean Corpuscular Volume (in fL)',
          'Mean Corpuscular Hemoglobin (in pg)',
          'Mean Corpuscular Hemoglobin Concentration (in g/L)',
          'Platelet count (per m³)',
          'RDW-CV (in %)',
          'RDW-SD (in %)',
          'RBC count (in ul)',
          'RBS (in mg/dL)',
          'Urine routine examination',
          'Relevant investigation if advised by specialist',
        ],
      },
      {
        sectionName: 'DLC',
        fields: ['Neutrophils (in %)', 'Lymphocytes (in %)', 'Monocytes (in %)', 'Eosinophils (in %)', 'Basophils (in %)'],
      },
      {
        sectionName: 'Diagnosis',
        fields: [
          'Diagnosis',
          'Already Known',
          'Other Disease / Diagnosis Notes',
          'National Health Programs',
          'Reason For Refer',
        ],
      },
      {
        sectionName: 'Reason For Refer Options',
        fields: ['For detailed evaluation', 'For treatment', 'Specialist consultation'],
      },
      { sectionName: 'Prescription', fields: ['Prescription (if any)'] },
    ],
  },
  CAT4: {
    category: 'CAT4',
    sections: [
      {
        sectionName: 'General Physical Examination',
        fields: [
          'Weight (in kg)',
          'Height (in cm)',
          'BMI (kg/m²)',
          'Pulse (per min)',
          'BP (mmHg)',
          'Pallor',
          'Jaundice/Cyanosis',
          'Clubbing',
          'Lymphadenopathy',
          'Pedal oedema',
        ],
      },
      {
        sectionName: 'Systemic Examination',
        fields: [
          'Chest',
          'CVS',
          'Per abdomen',
          'CNS',
          'Hearing',
          'Dental examination',
          'Genital examination',
          'Breast examination (for females)',
        ],
      },
      { sectionName: 'Eye - Refraction', fields: ['Right eye', 'Left eye', 'Colour blindness'] },
      {
        sectionName: 'Mandatory Investigations',
        fields: [
          'Hb (in g/dl)',
          'TLC (/cumm)',
          'Packed Cell Volume (in L/L)',
          'Mean Corpuscular Volume (in fL)',
          'Mean Corpuscular Hemoglobin (in pg)',
          'Mean Corpuscular Hemoglobin Concentration (in g/L)',
          'Platelet count (per m³)',
          'RDW-CV (in %)',
          'RDW-SD (in %)',
          'RBC count (in ul)',
          'RBS (in mg/dL)',
          'Serum cholesterol (in mg/dL)',
          'Blood urea (in mg/dL)',
          'Serum creatinine (in mg/dL)',
          'Urine routine examination',
          'Relevant investigation if advised by specialist',
        ],
      },
      {
        sectionName: 'DLC',
        fields: ['Neutrophils (in %)', 'Lymphocytes (in %)', 'Monocytes (in %)', 'Eosinophils (in %)', 'Basophils (in %)'],
      },
      {
        sectionName: 'Diagnosis',
        fields: [
          'Diagnosis',
          'Already Known',
          'Other Disease / Diagnosis Notes',
          'National Health Programs',
          'Reason For Refer',
        ],
      },
      {
        sectionName: 'Reason For Refer Options',
        fields: ['For detailed evaluation', 'For treatment', 'Specialist consultation'],
      },
      { sectionName: 'Prescription', fields: ['Prescription (if any)'] },
    ],
  },
  CAT5: {
    category: 'CAT5',
    sections: [
      {
        sectionName: 'General Physical Examination',
        fields: [
          'Weight (in kg)',
          'Height (in cm)',
          'BMI (kg/m²)',
          'Pulse (per min)',
          'BP (mmHg)',
          'Pallor',
          'Jaundice/Cyanosis',
          'Clubbing',
          'Lymphadenopathy',
          'Pedal oedema',
        ],
      },
      {
        sectionName: 'Systematic Examination',
        fields: [
          'Chest',
          'CVS',
          'Per abdomen',
          'CNS',
          'Hearing',
          'Dental examination',
          'Genital examination',
          'Breast examination (for females)',
          'Joint examination',
          'Oral examination',
        ],
      },
      { sectionName: 'Eye - Refraction', fields: ['Right eye', 'Left eye', 'Colour blindness'] },
      {
        sectionName: 'Mandatory Investigations',
        fields: [
          'Hb (in g/dl)',
          'TLC (/cumm)',
          'Packed Cell Volume (in L/L)',
          'Mean Corpuscular Volume (in fL)',
          'Mean Corpuscular Hemoglobin (in pg)',
          'Mean Corpuscular Hemoglobin Concentration (in g/L)',
          'Platelet count (per m³)',
          'RDW-CV (in %)',
          'RDW-SD (in %)',
          'RBC count (in ul)',
          'RBS (in mg/dL)',
          'Serum cholesterol (in mg/dL)',
          'Blood urea (in mg/dL)',
          'Serum creatinine (in mg/dL)',
          'TSH (in mU/L)',
          'PSA for males (in ng/mL)',
          'Urine routine examination',
          'VIA/PAP smear (for females)',
          'Relevant investigation if advised by specialist',
        ],
      },
      {
        sectionName: 'DLC',
        fields: ['Neutrophils (in %)', 'Lymphocytes (in %)', 'Monocytes (in %)', 'Eosinophils (in %)', 'Basophils (in %)'],
      },
      {
        sectionName: 'Diagnosis',
        fields: [
          'Diagnosis',
          'Already Known',
          'Other Disease / Diagnosis Notes',
          'National Health Programs',
          'Reason For Refer',
        ],
      },
      {
        sectionName: 'Reason For Refer Options',
        fields: ['For detailed evaluation', 'For treatment', 'Specialist consultation'],
      },
      { sectionName: 'Prescription', fields: ['Prescription (if any)'] },
    ],
  },
  CAT6: {
    category: 'CAT6',
    sections: [
      {
        sectionName: 'General Physical Examination',
        fields: ['Weight (in kg)', 'Height (in cm)', 'BMI (kg/m²)', 'Pulse (per min)', 'Respiratory rate (per min)', 'BP (mmHg)'],
      },
      {
        sectionName: 'Symptoms / Complaints',
        fields: ['Pallor', 'Jaundice/Cyanosis', 'Clubbing', 'Lymphadenopathy', 'Pedal oedema'],
      },
      {
        sectionName: 'Systematic Investigation',
        fields: [
          'Chest',
          'CVS',
          'Per abdomen',
          'CNS',
          'Hearing',
          'Dental examination',
          'Genital examination',
          'Breast examination (for females)',
          'Joint examination',
          'Oral examination',
        ],
      },
      { sectionName: 'Eye - Refraction', fields: ['Right eye', 'Left eye', 'Colour blindness'] },
      {
        sectionName: 'Mandatory Investigations',
        fields: [
          'Hb (in g/dl)',
          'TLC (/cumm)',
          'Packed Cell Volume (in L/L)',
          'Mean Corpuscular Volume (in fL)',
          'Mean Corpuscular Hemoglobin (in pg)',
          'Mean Corpuscular Hemoglobin Concentration (in g/L)',
          'Platelet count (per mm³)',
          'RDW-CV (in %)',
          'RDW-SD (in %)',
          'RBC count (in ul)',
          'RBS (in mg/dL)',
          'Serum cholesterol (in mg/dL)',
          'Blood urea (in mg/dL)',
          'Serum creatinine (in mg/dL)',
          'TSH (in mU/L)',
          'PSA for males (in ng/mL)',
          'Urine routine examination',
          'VIA/PAP smear (for females)',
          'Relevant investigation if advised by specialist',
        ],
      },
      {
        sectionName: 'DLC',
        fields: ['Neutrophils (in %)', 'Lymphocytes (in %)', 'Monocytes (in %)', 'Eosinophils (in %)', 'Basophils (in %)'],
      },
      {
        sectionName: 'Diagnosis',
        fields: [
          'Diagnosis',
          'Already Known',
          'Other Disease / Diagnosis Notes',
          'National Health Programs',
          'Reason For Refer',
        ],
      },
      {
        sectionName: 'Reason For Refer Options',
        fields: ['For detailed evaluation', 'For treatment', 'Specialist consultation'],
      },
      { sectionName: 'Prescription', fields: ['Prescription (if any)'] },
    ],
  },
};

export function buildCategoryForm(definition: CategoryFormDefinition): FormGroup {
  const sectionGroups: Record<string, FormGroup> = {};

  for (const section of definition.sections) {
    const controls: Record<string, FormControl<string>> = {};
    for (const field of section.fields) {
      controls[field] = new FormControl('', { nonNullable: true });
    }
    sectionGroups[section.sectionName] = new FormGroup(controls);
  }

  return new FormGroup(sectionGroups);
}

export function snapshotCategoryForm(definition: CategoryFormDefinition, form: FormGroup): CategoryFormPayload {
  const payload: CategoryFormPayload = {
    historySection: {},
    generalExamSection: {},
    systemicSection: {},
    mandatorySection: {},
    diagnosisSection: {},
    prescriptionSection: {},
  };
  const value = form.getRawValue() as Record<string, Record<string, string>>;

  for (const section of definition.sections) {
    const bucket = bucketForSection(section.sectionName);
    payload[bucket][section.sectionName] = value[section.sectionName] ?? {};
  }

  return payload;
}

export function patchCategoryForm(
  definition: CategoryFormDefinition,
  form: FormGroup,
  payload: CategoryFormPayload,
): void {
  for (const section of definition.sections) {
    const group = form.get(section.sectionName) as FormGroup | null;
    if (!group) {
      continue;
    }

    const bucket = toRecord(payload[bucketForSection(section.sectionName)]);
    const sectionValue = toRecord(bucket[section.sectionName]);
    const patch: Record<string, string> = {};

    for (const field of section.fields) {
      const value = sectionValue[field] ?? bucket[field] ?? '';
      patch[field] = value === null || value === undefined ? '' : String(value);
    }

    group.patchValue(patch, { emitEvent: false });
  }
}

export function screeningDataViewToPayload(record: ScreeningDataView): CategoryFormPayload {
  return {
    historySection: parseSection(record.historySection),
    generalExamSection: parseSection(record.generalExamSection),
    systemicSection: parseSection(record.systemicSection),
    mandatorySection: parseSection(record.mandatorySection),
    diagnosisSection: parseSection(record.diagnosisSection),
    prescriptionSection: parseSection(record.prescriptionSection),
  };
}

export function isLongTextField(sectionName: string, fieldName: string): boolean {
  const value = `${sectionName} ${fieldName}`.toLowerCase();
  return (
    value.includes('history') ||
    value.includes('notes') ||
    value.includes('reason') ||
    value.includes('prescription') ||
    value.includes('relevant investigation') ||
    value.includes('family history')
  );
}

function bucketForSection(sectionName: string): PayloadBucket {
  const normalized = sectionName.toLowerCase();

  if (normalized.includes('history') || normalized.includes('symptoms') || normalized.includes('milestones')) {
    return 'historySection';
  }

  if (normalized.includes('general physical')) {
    return 'generalExamSection';
  }

  if (normalized.includes('mandatory') || normalized === 'dlc') {
    return 'mandatorySection';
  }

  if (normalized.includes('diagnosis') || normalized.includes('reason for refer')) {
    return 'diagnosisSection';
  }

  if (normalized.includes('prescription')) {
    return 'prescriptionSection';
  }

  return 'systemicSection';
}

function parseSection(value: string | Record<string, unknown> | undefined): Record<string, unknown> {
  if (!value) {
    return {};
  }

  if (typeof value !== 'string') {
    return value;
  }

  try {
    const parsed = JSON.parse(value) as unknown;
    return toRecord(parsed);
  } catch {
    return {};
  }
}

function toRecord(value: unknown): Record<string, unknown> {
  return value && typeof value === 'object' && !Array.isArray(value) ? (value as Record<string, unknown>) : {};
}
