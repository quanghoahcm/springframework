import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { JhipsterRegionMySuffixModule } from './region-my-suffix/region-my-suffix.module';
import { JhipsterCountryMySuffixModule } from './country-my-suffix/country-my-suffix.module';
import { JhipsterLocationMySuffixModule } from './location-my-suffix/location-my-suffix.module';
import { JhipsterDepartmentMySuffixModule } from './department-my-suffix/department-my-suffix.module';
import { JhipsterTaskMySuffixModule } from './task-my-suffix/task-my-suffix.module';
import { JhipsterEmployeeMySuffixModule } from './employee-my-suffix/employee-my-suffix.module';
import { JhipsterJobMySuffixModule } from './job-my-suffix/job-my-suffix.module';
import { JhipsterJobHistoryMySuffixModule } from './job-history-my-suffix/job-history-my-suffix.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        JhipsterRegionMySuffixModule,
        JhipsterCountryMySuffixModule,
        JhipsterLocationMySuffixModule,
        JhipsterDepartmentMySuffixModule,
        JhipsterTaskMySuffixModule,
        JhipsterEmployeeMySuffixModule,
        JhipsterJobMySuffixModule,
        JhipsterJobHistoryMySuffixModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterEntityModule {}
