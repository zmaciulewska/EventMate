import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowcaseEditComponent } from './showcase-edit.component';

describe('ShowcaseEditComponent', () => {
  let component: ShowcaseEditComponent;
  let fixture: ComponentFixture<ShowcaseEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShowcaseEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowcaseEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
