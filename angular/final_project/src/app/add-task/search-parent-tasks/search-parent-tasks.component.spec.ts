import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchParentTasksComponent } from './search-parent-tasks.component';

describe('SearchParentTasksComponent', () => {
  let component: SearchParentTasksComponent;
  let fixture: ComponentFixture<SearchParentTasksComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SearchParentTasksComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchParentTasksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
