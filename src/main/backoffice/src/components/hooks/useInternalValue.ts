import React from "react";

interface InternalState {
  internalValue: string;
  editing: boolean;
}

interface SetEditingAction {
  type: "editing";
  payload: { editing: boolean };
}

interface SetValueAction {
  type: "value";
  payload: { internalValue: string };
}

type Action = SetEditingAction | SetValueAction;

function reducer(state: InternalState, action: Action) {
  switch (action.type) {
    case "editing":
      return { ...state, ...action.payload };
    case "value":
      if (!state.editing) {
        return state;
      }

      return { ...state, ...action.payload };
  }
}

export function useInternalValue(
  initialValue: string
): [InternalState, React.Dispatch<Action>] {
  return React.useReducer(reducer, {
    editing: false,
    internalValue: initialValue,
  });
}

export function setEditing(
  dispatch: React.Dispatch<Action>,
  editing: boolean
): void {
  dispatch({ type: "editing", payload: { editing } });
}

export function setInternalValue(
  dispatch: React.Dispatch<Action>,
  internalValue: string
): void {
  dispatch({ type: "value", payload: { internalValue } });
}
