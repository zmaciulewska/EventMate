import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowcaseDisplayComponent } from './showcase-display.component';

describe('ShowcaseDisplayComponent', () => {
  let component: ShowcaseDisplayComponent;
  let fixture: ComponentFixture<ShowcaseDisplayComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShowcaseDisplayComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowcaseDisplayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
